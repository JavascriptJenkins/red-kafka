package com.redkafka

import org.springframework.stereotype.Component


@Component
interface SFTPDataStream {


    boolean streamData()


    void endStreamProcess()


}