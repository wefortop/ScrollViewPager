# ScrollViewPager
一个高效的无限滚动解决方案

```
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
    
```
