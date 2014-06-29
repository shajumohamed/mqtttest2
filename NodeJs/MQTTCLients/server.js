var mqtt = require('mqtt');
var count=0;
client = mqtt.createClient(1883, 'localhost');

//client.subscribe('Allianz/#');
pu100();
function pu100()
{
	
client.publish('Allianz/Test', 'Hello From My Laptop '+count,{qos:1},function(){
	console.log(count);
if(count<100)
	{
		count++;

		pu100();
	}
});

}

client.on('message', function (topic, message) {
  console.log(message);
});