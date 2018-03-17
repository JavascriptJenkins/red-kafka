package com.redkafka.config

import com.jcraft.jsch.ChannelSftp
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.annotation.InboundChannelAdapter
import org.springframework.integration.annotation.Poller
import org.springframework.integration.config.EnableIntegration
import org.springframework.integration.file.filters.AcceptAllFileListFilter
import org.springframework.integration.file.remote.session.CachingSessionFactory
import org.springframework.integration.file.remote.session.SessionFactory
import org.springframework.integration.sftp.inbound.SftpStreamingMessageSource
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory
import org.springframework.integration.sftp.session.SftpRemoteFileTemplate


@Configuration
@EnableIntegration
class SFTPStreamConfig {


    @Bean
    SessionFactory<ChannelSftp.LsEntry> sftpSessionFactory(){
        DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory(isSharedSession: true)
        factory.setHost()
        factory.setPort()
        factory.setUser()
        factory.setPassword()
        factory.setAllowUnknownKeys(true)
        return new CachingSessionFactory<ChannelSftp.LsEntry>(factory)
    }


    @Bean
    SftpRemoteFileTemplate template(){
        return new SftpRemoteFileTemplate(sftpSessionFactory())
    }


    @Bean
    @InboundChannelAdapter(channel="DATA-STREAM", autoStartup = "false", poller = @Poller(fixedDelay = "100"))
    SftpStreamingMessageSource sftpMessageSource(){
        SftpStreamingMessageSource messageSource = new SftpStreamingMessageSource(template())
        messageSource.setRemoteDirectory()
        messageSource.setFilter(new AcceptAllFileListFilter<ChannelSftp.LsEntry>())
        messageSource.setMaxFetchSize(1)
        return messageSource
    }









}
