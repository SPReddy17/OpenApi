package com.android.openapi.ui.main.account

import androidx.lifecycle.LiveData
import com.android.openapi.models.AccountProperties
import com.android.openapi.repository.main.AccountRepository
import com.android.openapi.session.SessionManager
import com.android.openapi.ui.BaseViewModel
import com.android.openapi.ui.DataState
import com.android.openapi.ui.main.account.state.AccountStateEvent
import com.android.openapi.ui.main.account.state.AccountStateEvent.*
import com.android.openapi.ui.main.account.state.AccountViewState
import com.android.openapi.util.AbsentLiveData
import javax.inject.Inject


class AccountViewModel
@Inject
constructor(
    val sessionManager: SessionManager,
    val accountRepository: AccountRepository
)
    : BaseViewModel<AccountStateEvent, AccountViewState>()
{
    override fun handleStateEvent(stateEvent: AccountStateEvent): LiveData<DataState<AccountViewState>> {
        when(stateEvent){

            is AccountStateEvent.GetAccountPropertiesEvent ->{
                return AbsentLiveData.create()
            }
            is AccountStateEvent.UpdateAccountPropertiesEvent ->{
                return AbsentLiveData.create()
            }
            is AccountStateEvent.ChangePasswordEvent ->{
                return AbsentLiveData.create()
            }
            is None ->{
                return AbsentLiveData.create()
            }
        }
    }

    override fun initNewViewState(): AccountViewState {
        return AccountViewState()
    }

    fun setAccountPropertiesData(accountProperties: AccountProperties){
        val update = getCurrentViewStateOrNew()
        if(update.accountProperties == accountProperties){
            return
        }
        update.accountProperties = accountProperties
        _viewState.value = update
    }

    fun logout(){
        sessionManager.logout()
    }

}







