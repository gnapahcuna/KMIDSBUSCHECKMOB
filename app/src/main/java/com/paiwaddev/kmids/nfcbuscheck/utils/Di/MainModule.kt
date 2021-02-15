package com.paiwaddev.kmids.kmidsmobile.utils.Di

import android.app.Activity
import com.paiwaddev.kmids.kmidsmobile.useCase.LoginUseCase
import com.paiwaddev.kmids.kmidsmobile.view.adapter.SettingModel
import com.paiwaddev.kmids.kmidsmobile.view.adapter.SettingsAdapter
import com.paiwaddev.kmids.nfcbuscheck.data.repo.fcm.FCMRepository
import com.paiwaddev.kmids.nfcbuscheck.data.repo.fcm.FCMRepositoryImpl
import com.paiwaddev.kmids.nfcbuscheck.views.custom.ProgressBuilder
import com.paiwaddev.kmids.nfcbuscheck.viewModel.LoginViewModel
import com.paiwaddev.kmids.nfcbuscheck.views.ui.home.HomeViewModel
import com.paiwaddev.kmids.nfcbuscheck.data.repo.main.MainRepository
import com.paiwaddev.kmids.nfcbuscheck.data.repo.main.MainRepositoryImpl
import com.paiwaddev.kmids.nfcbuscheck.viewModel.SendNotificationsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

    //inject setting adapter
    factory { (data: List<SettingModel>, itemLister: SettingsAdapter.ItemListener, touchSwitchListener: SettingsAdapter.SwitchListener) -> SettingsAdapter(data, itemLister,touchSwitchListener) }

    //inject repo
    factory<MainRepository> { MainRepositoryImpl(get()) }
    factory<FCMRepository> { FCMRepositoryImpl(get()) }

    //inject use case
    single { LoginUseCase(get()) }

    //inject viewModel
    viewModel { LoginViewModel(get(),androidContext()) }
    viewModel { SendNotificationsViewModel(get()) }
    //inject viewModel share
    single { HomeViewModel(get(),androidContext()) }
    //inject busChild adapter
    //factory { (fm: FragmentManager,fragments: List<Fragment>) -> BusChildAdapter(fm, fragments) }

    //inject progress dialog
    single { (activity: Activity) -> ProgressBuilder(activity) }
}