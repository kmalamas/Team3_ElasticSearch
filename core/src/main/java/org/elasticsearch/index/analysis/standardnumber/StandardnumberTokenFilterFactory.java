/*
 * Copyright (C) 2014 Jörg Prante
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, see http://www.gnu.org/licenses
 * or write to the Free Software Foundation, Inc., 51 Franklin Street,
 * Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * The interactive user interfaces in modified source and object code
 * versions of this program must display Appropriate Legal Notices,
 * as required under Section 5 of the GNU Affero General Public License.
 *
 */
package org.elasticsearch.index.analysis.standardnumber;

import org.apache.lucene.analysis.TokenStream;
import org.elasticsearch.common.inject.Injector;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.analysis.AbstractTokenFilterFactory;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.mapper.standardnumber.StandardnumberService;

public class StandardnumberTokenFilterFactory extends AbstractTokenFilterFactory  {

    private final Settings settings;

    public StandardnumberTokenFilterFactory(IndexSettings indexSettings,
                                            Environment environment,
                                            String name,
                                            Settings settings) {
        super(indexSettings, name, settings);

        this.settings = settings;
    }

    @Override

    public TokenStream create(TokenStream tokenStream) {

        StandardnumberService service = new StandardnumberService(settings);

        return new StandardnumberTokenFilter(tokenStream, service, settings);

    }
}
