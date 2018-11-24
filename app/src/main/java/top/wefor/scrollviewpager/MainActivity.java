package top.wefor.scrollviewpager;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 2018/11/24.
 *
 * @author ice
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button5) Button mButton5;
    @BindView(R.id.button4) Button mButton4;
    @BindView(R.id.button3) Button mButton3;
    @BindView(R.id.button2) Button mButton2;
    @BindView(R.id.button1) Button mButton1;
    @BindView(R.id.button0) Button mButton0;

    private int mShowSize = 4;
    private int mTotalSize = mShowSize + 2;

    private Button[] mButtonArray = new Button[mTotalSize];
    private int[] mStepArray = new int[mTotalSize];
    private int mMoveX;
    private int mHead = 0;

    private int mSendInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        ButterKnife.bind(this);

        mButtonArray[0] = mButton0;
        mButtonArray[1] = mButton1;
        mButtonArray[2] = mButton2;
        mButtonArray[3] = mButton3;
        mButtonArray[4] = mButton4;
        mButtonArray[5] = mButton5;

        mStepArray[0] = 0;
        mStepArray[1] = -1;
        mStepArray[2] = -2;
        mStepArray[3] = -3;
        mStepArray[4] = -4;
        mStepArray[5] = -5;

        mButton0.post(() -> {
            mMoveX = mButton0.getWidth();
        });

        startTimer();
    }

    private void startTimer() {
        Random random = new Random();

        new CountDownTimer(10 * 60_000, 2_000) {

            @Override
            public void onTick(long l) {
                if (random.nextBoolean()) {
                    move("" + mSendInt++);
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    /*挪动整个视图列表*/
    private void move(String text) {
        int pos = nextHead();

        //重置头item的内容
        mButtonArray[pos].setText(text);

        //执行滚动逻辑
        for (int i = 0; i < mTotalSize; i++) {
            Button button = mButtonArray[pos];
            //获取该当前item的下一步
            int step = (mStepArray[pos] + 1) % mTotalSize;
            mStepArray[pos] = step;
            //根据步得到需要挪动到的x坐标
            float translationX = mMoveX * (step - 1);
            if (step == 1) {
                //挪动头item
                button.animate().translationX(translationX).alpha(1).start();
            } else if (step == (mTotalSize - 1)) {
                //挪动尾item
                button.animate().translationX(translationX).alpha(0).start();
            } else {
                //挪动其他item
                button.animate().translationX(translationX).start();
            }
            pos = next(pos);
        }
    }

    /*获取下一个首位*/
    private int nextHead() {
        //待显示位。
        int pos = mHead;
        //下一次的待显示位
        mHead = (mHead + 1) % mTotalSize;
        return pos;
    }

    /*获取当前位置的下一位*/
    private int next(int pos) {
        return (pos + (mTotalSize - 1)) % mTotalSize;
    }

}
