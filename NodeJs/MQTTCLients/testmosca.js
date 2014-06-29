var mqtt = require('mqtt');
var count=0;
var client = mqtt.createClient(1883, 'test.mosquitto.org');


	
client.publish('Allianz/bloodGroups/APositive', 'Apos ',{qos:1},function(){
	console.log(count);
});
	
client.publish('abcd', 'Apos ',function(){
	console.log(count);
});
client.publish('Allianz/bloodGroups/OPositive', 'Opos ',function(){
	console.log(count);
});
client.publish('Allianz/bloodGroups/ABPositive', 'ABpos ',function(){
	console.log(count);
});
// client.publish('Allianz/bloodGroups/#', 'Message ',function(){
// 	console.log(count);
// });

client.on('message', function (topic, message) {
  console.log(message);
});