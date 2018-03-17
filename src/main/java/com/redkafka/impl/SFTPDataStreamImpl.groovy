package com.redkafka.impl

import com.redkafka.KafkaProducerConfig
import com.redkafka.SFTPDataStream
import org.apache.kafka.clients.producer.KafkaProducer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.integration.config.EnableIntegration
import org.springframework.integration.endpoint.SourcePollingChannelAdapter
import org.springframework.integration.sftp.inbound.SftpStreamingMessageSource
import org.springframework.messaging.Message
import org.springframework.stereotype.Component


@Component
@EnableIntegration
class SFTPDataStreamImpl implements SFTPDataStream{

    @Autowired
    SourcePollingChannelAdapter sourcePollingChannelAdapter

    @Autowired
    SftpStreamingMessageSource sftpStreamingMessageSource

    @Autowired
    KafkaProducerConfig kafkaProducer

    InputStream inputStream

    private final ArrayList filesProcessedList = new ArrayList()


    @Override
    boolean streamData() {

        String datadirectory = "data directory"

        sftpStreamingMessageSource.setRemoteDirectory(datadirecttory)

        // this step is not technically needed
        sourcePollingChannelAdapter.setSource(sftpStreamingMessageSource)

        Message<InputStream> inputStreamMessage = sftpStreamingMessageSource.receive()

        streamFileData(inputStreamMessage)


        return false
    }


    private streamFileData(Message<InputStream> inputStreamMessage){

        String fileName = inputStreamMessage.headers.get("file_remoteFile")

        if(filesProcessedList.contains(fileName)){




            endStreamProcess()
        }
        else {

            inputStream = inputStreamMessage.payload

            Scanner scanner = new Scanner(inputStream, "UTF-8").useDelimiter("\\A")

            while(scanner.hasNext()){

                String string = scanner.nextLine()

                kafkaProducer.sendMessage(string)


            }

            filesProcessedList.add(fileName)


            inputStream.close()


            // call the method recursively to look for another file
            streamData()


        }


    }

    @Override
    void endStreamProcess() {

        // the channel adapter is running in the background
        sourcePollingChannelAdapter.stop()
    }
}

