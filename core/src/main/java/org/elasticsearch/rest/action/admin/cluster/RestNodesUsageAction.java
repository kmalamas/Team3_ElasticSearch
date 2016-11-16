package org.elasticsearch.rest.action.admin.cluster;

/**
 * Created by lundenant on 2016-11-16.
 */
public class RestNodesUsageAction {
}
/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.rest.action.admin.cluster;

        import org.elasticsearch.action.admin.cluster.node.usage.NodesUsageRequest;
        import org.elasticsearch.action.admin.cluster.node.usage.NodesUsageResponse;
        import org.elasticsearch.client.node.NodeClient;
        import org.elasticsearch.common.Strings;
        import org.elasticsearch.common.inject.Inject;
        import org.elasticsearch.common.settings.Settings;
        import org.elasticsearch.common.xcontent.XContentBuilder;
        import org.elasticsearch.rest.BaseRestHandler;
        import org.elasticsearch.rest.BytesRestResponse;
        import org.elasticsearch.rest.RestChannel;
        import org.elasticsearch.rest.RestController;
        import org.elasticsearch.rest.RestRequest;
        import org.elasticsearch.rest.RestResponse;
        import org.elasticsearch.rest.RestStatus;
        import org.elasticsearch.rest.action.RestActions;
        import org.elasticsearch.rest.action.RestBuilderListener;

        import java.util.Set;

        import static org.elasticsearch.rest.RestRequest.Method.GET;

public class RestNodesUsageAction extends BaseRestHandler {

    @Inject
    public RestNodesUsageAction(Settings settings, RestController controller) {
        super(settings);
        controller.registerHandler(GET, "/_nodes/usage", this);
        controller.registerHandler(GET, "/_nodes/{nodeId}/usage", this);

        controller.registerHandler(GET, "/_nodes/usage/{metric}", this);
        controller.registerHandler(GET, "/_nodes/{nodeId}/usage/{metric}", this);
    }

    @Override
    public void handleRequest(final RestRequest request, final RestChannel channel, final NodeClient client) {
        String[] nodesIds = Strings.splitStringByCommaToArray(request.param("nodeId"));
        Set<String> metrics = Strings.splitStringByCommaToSet(request.param("metric", "_all"));

        NodesUsageRequest nodesUsageRequest = new NodesUsageRequest(nodesIds);
        nodesUsageRequest.timeout(request.param("timeout"));

        if (metrics.size() == 1 && metrics.contains("_all")) {
            nodesUsageRequest.all();
        } else {
            nodesUsageRequest.clear();
            nodesUsageRequest.restActions(metrics.contains("rest_actions"));
        }

        client.admin().cluster().nodesUsage(nodesUsageRequest, new RestBuilderListener<NodesUsageResponse>(channel) {

            @Override
            public RestResponse buildResponse(NodesUsageResponse response, XContentBuilder builder) throws Exception {
                builder.startObject();
                RestActions.buildNodesHeader(builder, channel.request(), response);
                builder.field("cluster_name", response.getClusterName().value());
                response.toXContent(builder, channel.request());
                builder.endObject();

                return new BytesRestResponse(RestStatus.OK, builder);
            }
        });
    }

    @Override
    public boolean canTripCircuitBreaker() {
        return false;
    }
}