package pack.program.nutritional.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailViewModel : ViewModel() {

    val _secondFragTextTest = MutableLiveData("secondFragTextTest")  //Ue this Text to pass any values

    /**
     * Live data for Dummy Text
     */
    val secondFragTextTest: LiveData<String>
            get() = _secondFragTextTest

}