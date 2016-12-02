userName=$1
event=$2
if [ "$userName" == "homer" ]; then
    userId=3044012
else
    echo "This demo works only for homer. Sorry!"
    exit 0
fi

if [ "$event" == "Half Dome day hike" ]; then
    eventId=2877501688
elif [ "$event" == "Bollywood night" ]; then
    eventId=799782433
else
    echo "Event not valid"
    exit 0
fi

result=`java -classpath $(hbase classpath):.:Scoring.class Scoring $userId $eventId 2> /dev/null`

if [ "$result" == "true" ]; then
    echo "$userName might like to attend the $event."
elif [ "$result" == "false" ]; then
    echo "$userName may not like to attend the $event"
else
    echo "Unable to recommend anything for $userName"
fi
