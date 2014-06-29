
var mqtt = require('mqtt')

client = mqtt.createClient(1883, 'localhost',{
  clientId: "shajuabcd",
  clean: false
});

client.subscribe('Allianz/#',{ qos: 1 },
  function() {
  
  console.log("subscribe done!");
  // called when the subscribe is successful
  client.end();
});



