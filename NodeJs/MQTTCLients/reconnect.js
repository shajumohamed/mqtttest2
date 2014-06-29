var mqtt = require('mqtt')

client = mqtt.createClient(1883, 'localhost',{
  clientId: "shajuabcd",
  clean: false
});
client.subscribe('Allianz/#',{ qos: 1 });
client.on('message', function (topic, message) {
  console.log(message);
});


