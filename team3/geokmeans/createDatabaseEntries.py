import shapefile
import urllib2
import json
sf = shapefile.Reader("places")
sr = sf.shapeRecords()

for r in sr:
    try :
        if r.record[2].strip() and r.record[3].strip():
            req = urllib2.Request('http://localhost:9200/places/place/')
            req.add_header('Content-Type', 'application/json')
            data = {'id': r.record[0].strip(),'name':r.record[2].strip(),'type':r.record[3].strip(),'location':{'lat':r.shape.points[0][1],'lon':r.shape.points[0][0]}}
            response = urllib2.urlopen(req, json.dumps(data))
            print r.record[2]
    except Exception,e: 
        print e
        #print "ERROR ",r.record[0],r.record[2],r.record[3] , r.shape.points[0][0], r.shape.points[0][1]
        pass