package com.catpuppyapp.puppygit.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import com.catpuppyapp.puppygit.play.pro.R
import com.catpuppyapp.puppygit.constants.Cons
import com.catpuppyapp.puppygit.dto.FileSimpleDto
import com.catpuppyapp.puppygit.utils.state.CustomStateSaveable
import com.catpuppyapp.puppygit.utils.state.StateUtil
import com.catpuppyapp.puppygit.fileeditor.texteditor.state.TextEditorState
import kotlinx.coroutines.CoroutineScope
import java.io.File

//private val TAG = "ComposeHelper"
object ComposeHelper {
//    @Composable
//    fun getCoroutineScope():CoroutineScope {
//        return rememberCoroutineScope()
//    }



    @Composable
    fun getDoubleClickBackHandler(
        appContext: Context,
        openDrawer:() -> Unit,
        exitApp: () -> Unit
    ): () -> Unit {
        val backStartSec = rememberSaveable { mutableLongStateOf(0) }
        val pressBackAgainForExitText = stringResource(R.string.press_back_again_to_exit);
        val showTextAndUpdateTimeForPressBackBtn = {
            openDrawer()
            showToast(appContext, pressBackAgainForExitText, Toast.LENGTH_SHORT)
            backStartSec.longValue = getSecFromTime() + Cons.pressBackDoubleTimesInThisSecWillExit
        }

        val backHandlerOnBack = {
            //如果在两秒内按返回键，就会退出，否则会提示再按一次可退出程序
            if (backStartSec.longValue > 0 && getSecFromTime() <= backStartSec.longValue) {  //大于0说明不是第一次执行此方法，那检测是上次获取的秒数，否则直接显示“再按一次退出app”的提示
                exitApp()
            } else {
                showTextAndUpdateTimeForPressBackBtn()
            }
        }
        return backHandlerOnBack
    }

}
