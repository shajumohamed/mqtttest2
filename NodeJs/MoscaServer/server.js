var mosca = require('mosca')

var ascoltatore = {
  //using ascoltatore
  type: 'mongo',
  url: 'mongodb://localhost:27017/mqtt',
  pubsubCollection: 'ascoltatori',
  mongo: {}
};
var persis={ url: 'mongodb://localhost:27017/mqtt',};


var settings = {
  port: 1883,
  backend: ascoltatore
};

var server = new mosca.Server(settings);
//var db = new mosca.persistence.MongoPersistence({ url: "mongodb://localhost:27017/mqtt",ttl:{subscriptions:10000,packets:10000},mongo:{} });
//db.wire(server);

server.on('clientConnected', function(client) {
    console.log('client connected', client.id);
});

// fired when a message is received
server.on('published', function(packet, client) {
  console.log('Published', packet.payload);
});

var onPersistenceReady = function()
{
    console.log('Persistence Ready');
    persistence.wire(server);
}

server.on('ready', setup);

var persistence = mosca.persistence.Mongo(persis, onPersistenceReady );

// fired when the mqtt server is ready
function setup() {
  console.log('Mosca server is up and running')
}