package app.expense.domain.di

import app.expense.api.SMSReadAPI
import app.expense.api.TransactionSyncAPI
import app.expense.domain.smsTemplate.SMSTemplateMatcher
import app.expense.domain.smsTemplate.SMSTemplateProvider
import app.expense.domain.transaction.TransactionDetector
import app.expense.domain.transaction.TransactionSyncService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DomainModule {

    @Provides
    fun provideSMSTemplateProvider(): SMSTemplateProvider {
        return SMSTemplateProvider()
    }

    @Provides
    fun provideSMSTemplateMatcher(): SMSTemplateMatcher {
        return SMSTemplateMatcher()
    }

    @Provides
    fun provideTransactionDetector(
        smsTemplateProvider: SMSTemplateProvider,
        smsTemplateMatcher: SMSTemplateMatcher
    ): TransactionDetector {
        return TransactionDetector(smsTemplateProvider, smsTemplateMatcher)
    }

    @Provides
    fun transactionSyncService(
        transactionSyncAPI: TransactionSyncAPI,
        smsReadAPI: SMSReadAPI,
        transactionDetector: TransactionDetector
    ): TransactionSyncService {
        return TransactionSyncService(transactionSyncAPI, smsReadAPI, transactionDetector)
    }
}