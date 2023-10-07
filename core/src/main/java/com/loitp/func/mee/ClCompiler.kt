import com.loitp.func.mee.eva.EvaResult
import com.loitp.func.mee.eva.Evaluator
import com.loitp.func.mee.eva.getLastResultValue
import com.loitp.func.mee.source.ISource

/**
 ** Author : Abdelmajid ID ALI
 ** On : 17/08/2021
 ** Email :  abdelmajid.idali@gmail.com
 **/
class ClCompiler {

    fun compile(source: ISource): Double {
        return fullResult(source).getLastResultValue()
    }

    fun fullResult(source: ISource): EvaResult {
        val evaluator = Evaluator(source)
        return evaluator.evaluateResult()
    }
}