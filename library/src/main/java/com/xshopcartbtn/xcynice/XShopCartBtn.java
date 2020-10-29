package com.xshopcartbtn.xcynice;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.xshopcartbtn.xcynice.listener.OnChangeValueListener;
import com.xshopcartbtn.xcynice.listener.OnWarnListener;

/**
 * @Author 许朋友爱玩
 * @Date 2020/10/29 21:54
 * @Github https://github.com/LoveLifeEveryday
 * @JueJin https://juejin.im/user/5e429bbc5188254967066d1b/posts
 * @Description 购物车加减控件
 */

@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class XShopCartBtn extends LinearLayout implements View.OnClickListener, TextWatcher {
    /**
     * 最大购买数量，默认最大值
     */
    private int mBuyMax = Integer.MAX_VALUE;

    /**
     * 购买数量
     */
    private int inputValue = 1;


    /**
     * 商品库存，默认最大值
     */
    private int inventory = Integer.MAX_VALUE;

    /**
     * 商品最小购买数量，默认值为1
     */
    private int mBuyMin = 1;

    /**
     * 步长--每次增加的个数，默认是1
     */
    private int mStep = 1;


    /**
     * 设置改变的位置，默认是0;
     * 集合数据中会用到
     */
    private int mPosition = 0;


    private OnWarnListener mOnWarnListener;
    private OnChangeValueListener mOnChangeValueListener;
    private EditText mEtInput;

    public XShopCartBtn(Context context) {
        super(context, null);
    }

    public XShopCartBtn(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public XShopCartBtn(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 初始化属性
     *
     * @param context context
     * @param attrs   attrs 属性
     */
    private void init(Context context, AttributeSet attrs) {
        //得到属性
        if (attrs != null) {
            @SuppressLint("CustomViewStyleable") TypedArray typeArray = getContext().obtainStyledAttributes(attrs, R.styleable.AddSubUtils);
            //是否可以点击
            boolean editable = typeArray.getBoolean(R.styleable.AddSubUtils_editable, true);
            // 输入框的位置
            String location = typeArray.getString(R.styleable.AddSubUtils_location);
            // 左右两面的宽度
            int imageWidth = typeArray.getDimensionPixelSize(R.styleable.AddSubUtils_ImageWidth, -1);
            // 中间内容框的宽度
            int contentWidth = typeArray.getDimensionPixelSize(R.styleable.AddSubUtils_contentWidth, -1);
            // 中间字体的大小
            int contentTextSize = typeArray.getDimensionPixelSize(R.styleable.AddSubUtils_contentTextSize, -1);
            // 中间字体的颜色
            int contentTextColor = typeArray.getColor(R.styleable.AddSubUtils_contentTextColor, 0xff000000);
            // 整个控件的background
            Drawable background = typeArray.getDrawable(R.styleable.AddSubUtils_all_background);
            // 左面控件的背景
            Drawable leftBackground = typeArray.getDrawable(R.styleable.AddSubUtils_leftBackground);
            // 右面控件的背景
            Drawable rightBackground = typeArray.getDrawable(R.styleable.AddSubUtils_rightBackground);
            // 中间控件的背景
            Drawable contentBackground = typeArray.getDrawable(R.styleable.AddSubUtils_contentBackground);
            // 左面控件的资源
            Drawable leftResources = typeArray.getDrawable(R.styleable.AddSubUtils_leftResources);
            // 右面控件的资源
            Drawable rightResources = typeArray.getDrawable(R.styleable.AddSubUtils_rightResources);
            // 资源回收
            typeArray.recycle();

            //根据位置显示不同的布局
            if ("start".equals(location)) {
                LayoutInflater.from(context).inflate(R.layout.add_sub_start_layout, this);
            } else if ("end".equals(location)) {
                LayoutInflater.from(context).inflate(R.layout.add_sub_end_layout, this);
            } else {
                LayoutInflater.from(context).inflate(R.layout.add_sub_layout, this);
            }

            ImageView icPlus = findViewById(R.id.ic_plus);
            ImageView icMinus = findViewById(R.id.ic_minus);
            mEtInput = findViewById(R.id.et_input);

            icPlus.setOnClickListener(this);
            icMinus.setOnClickListener(this);
            mEtInput.addTextChangedListener(this);

            setEditable(editable);
            mEtInput.setTextColor(contentTextColor);

            // 设置两边按钮的宽度
            if (imageWidth > 0) {
                LayoutParams textParams = new LayoutParams(imageWidth, LayoutParams.MATCH_PARENT);
                icPlus.setLayoutParams(textParams);
                icMinus.setLayoutParams(textParams);
            }

            // 设置中间输入框的宽度
            if (contentWidth > 0) {
                LayoutParams textParams = new LayoutParams(contentWidth, LayoutParams.MATCH_PARENT);
                mEtInput.setLayoutParams(textParams);
            }

            //  设置了文字的颜色
            if (contentTextColor > 0) {
                mEtInput.setTextSize(contentTextColor);
            }

            // 设置了文字的尺寸
            if (contentTextSize > 0) {
                mEtInput.setTextSize(contentTextSize);
            }

            //设置背景
            if (background != null) {
                setBackground(background);
            } else {
                setBackgroundResource(R.drawable.xshop_cart_btn_add_sub_bg);
            }


            //设置输入内容的背景
            if (contentBackground != null) {
                mEtInput.setBackground(contentBackground);
            }

            if (leftBackground != null) {
                icMinus.setBackground(leftBackground);
            }

            if (rightBackground != null) {
                icPlus.setBackground(rightBackground);
            }

            if (leftResources != null) {
                icMinus.setImageDrawable(leftResources);
            }

            if (rightResources != null) {
                icPlus.setImageDrawable(rightResources);
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ic_plus) {
            // 加
            if (inputValue < Math.min(mBuyMax, inventory)) {
                inputValue += mStep;
                //正常添加
                mEtInput.setText("" + inputValue);
            } else if (inventory < mBuyMax) {
                //库存不足
                warningForInventory();
            } else {
                //超过最大购买数
                warningForBuyMax();
            }
        } else if (id == R.id.ic_minus) {
            // 减
            if (inputValue > mBuyMin) {
                // 高于最小购买数
                inputValue -= mStep;
                mEtInput.setText(inputValue + "");
            } else {
                // 低于最小购买数
                warningForBuyMin();
            }
        } else if (id == R.id.et_input) {
            // 输入框
            mEtInput.setSelection(mEtInput.getText().toString().length());
        }
    }

    /**
     * 低于最小购买数
     * Warning for buy min.
     */
    private void warningForBuyMin() {
        if (mOnWarnListener != null) {
            mOnWarnListener.onWarningForBuyMin(mBuyMin);
        }
    }


    /**
     * 超过的最大购买数限制
     * Warning for buy max.
     */
    private void warningForBuyMax() {
        if (mOnWarnListener != null) {
            mOnWarnListener.onWarningForBuyMax(mBuyMax);
        }
    }


    /**
     * 超过的库存限制
     * Warning for inventory.
     */
    private void warningForInventory() {
        if (mOnWarnListener != null) {
            mOnWarnListener.onWarningForInventory(inventory);
        }
    }


    /**
     * 设置输入框是否可以点击
     *
     * @param editable 是否可以点击
     */
    private void setEditable(boolean editable) {
        if (editable) {
            mEtInput.setFocusable(true);
            mEtInput.setKeyListener(new DigitsKeyListener());
        } else {
            mEtInput.setFocusable(false);
            mEtInput.setKeyListener(null);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        onNumberInput();
    }


    @Override
    public void afterTextChanged(Editable s) {

    }

    /**
     * 监听输入的数据变化
     */
    @SuppressLint("SetTextI18n")
    private void onNumberInput() {
        //当前数量
        int count = getNumber();
        if (count < mBuyMin) {
            // 如果手动输入的小于最小购买数，自动变成最小购买数
            inputValue = mBuyMin;
            mEtInput.setText(inputValue + "");
            if (mOnChangeValueListener != null) {
                mOnChangeValueListener.onChangeValue(inputValue, mPosition);
            }
            return;
        }
        int limit = Math.min(mBuyMax, inventory);
        if (count > limit) {
            if (inventory < mBuyMax) {
                //库存不足
                warningForInventory();
            } else {
                //超过最大购买数
                warningForBuyMax();
            }
        } else {
            inputValue = count;
            if (mOnChangeValueListener != null) {
                mOnChangeValueListener.onChangeValue(inputValue, mPosition);
            }
        }
    }

    /**
     * 设置当前购物车的数量
     *
     * @param currentNumber 当前数量
     * @return 购物车加减按钮
     */
    @SuppressLint("SetTextI18n")
    public XShopCartBtn setCurrentNumber(int currentNumber) {
        if (currentNumber < mBuyMin) {
            inputValue = mBuyMin;
        } else {
            inputValue = Math.min(Math.min(mBuyMax, inventory), currentNumber);
        }
        mEtInput.setText(inputValue + "");
        return this;
    }

    /**
     * 得到当前库存
     *
     * @return 库存
     */
    public int getInventory() {
        return inventory;
    }

    /**
     * 设置库存
     *
     * @param inventory 库存
     * @return XShopCartBtn
     */
    public XShopCartBtn setInventory(int inventory) {
        this.inventory = inventory;
        return this;
    }

    public int getBuyMax() {
        return mBuyMax;
    }

    public XShopCartBtn setBuyMax(int buyMax) {
        mBuyMax = buyMax;
        return this;
    }

    /**
     * 在listView 或者 RecyclerView 中调用，防止子项因为布局重用机制而混乱
     *
     * @param position 位置
     * @return XShopCartBtn
     */
    public XShopCartBtn setPosition(int position) {
        mPosition = position;
        return this;
    }

    public int getPosition() {
        return mPosition;
    }

    public XShopCartBtn setBuyMin(int buyMin) {
        mBuyMin = buyMin;
        return this;
    }

    public XShopCartBtn setOnChangeValueListener(OnChangeValueListener onChangeValueListener) {
        mOnChangeValueListener = onChangeValueListener;
        return this;
    }

    public int getStep() {
        return mStep;
    }

    public XShopCartBtn setStep(int step) {
        mStep = step;
        return this;
    }


    public XShopCartBtn setOnWarnListener(OnWarnListener onWarnListener) {
        mOnWarnListener = onWarnListener;
        return this;
    }


    /**
     * 得到输入框的数量
     *
     * @return 输入框的数量
     */
    @SuppressLint("SetTextI18n")
    public int getNumber() {
        try {
            return Integer.parseInt(mEtInput.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        mEtInput.setText(mBuyMin + "");
        return mBuyMin;
    }
}
