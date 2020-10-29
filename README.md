# 商城购物车加减控件  XShopCartBtn

> 对购物车加减按钮控件的简单封装，几行代码就搞定，采用链式调用，而且样式支持自定义，并且在ListView中和RecyclerView中同样适用.这里的使用步骤讲的不是很细致，需要的朋友可以看下博客，里面讲了封装的过程和想法。如果你还在为商城购物车中的加减控件而烦恼，不妨试一下这个加减控件，相信你会爱上它。如果觉得有用，欢迎star和fork.

**Step 1.** Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

```groovy
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

**Step 2.** Add the dependency

```groovy
	dependencies {
	        implementation 'com.github.LoveLifeEveryday:XShopCartBtn:1.0.0'
	}
```

## 实例效果

![addsubutils_all.gif](https://github.com/Jmengfei/AddSubUtils/raw/master/image/addsubutils_all.gif)

## Attributes属性（addsubutils布局文件中调用）

| Attributes        | forma           | describe                               |
| ----------------- | --------------- | -------------------------------------- |
| editable          | boolean         | 是否可以手动输入                       |
| location          | string          | 输入框的位置(在左边还是右边)，默认中间 |
| ImageWidth        | dimension       | 左右2边+-按钮的宽度                    |
| contentWidth      | dimension       | 中间EditText的宽度                     |
| contentTextSize   | dimension       | 中间EditText的字体大小                 |
| contentTextColor  | color           | 中间字体的颜色                         |
| all_background    | color/reference | 整个控件的background                   |
| leftBackground    | color/reference | 左面控件的背景                         |
| rightBackground   | color/reference | 右面控件的背景                         |
| contentBackground | color/reference | 中间控件的背景                         |
| leftResources     | color/reference | 左面控件的资源                         |
| rightResources    | color/reference | 右面控件的资源                         |

# 使用步骤

### 1.依赖AddSubUtils

```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
```groovy
dependencies {
implementation 'com.github.LoveLifeEveryday:XShopCartBtn:1.0.0' // 最新版本
}
```

### 2. 在xml代码中引用

```xml
    <com.xshopcartbtn.xcynice.XShopCartBtn
        android:id="@+id/add_sub"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
```

你也可以自定义样式：

```xml
 <com.xshopcartbtn.xcynice.XShopCartBtn
        android:id="@+id/add_sub_2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="10dp"
        app:editable="true"
        app:ImageWidth="60dp"
        app:contentTextColor="@color/colorText"
        app:contentWidth="120dp"
        app:contentTextSize="16sp"
        app:contentBackground="@color/material_teal_200"
        app:leftBackground="@drawable/left_selector"
        app:rightBackground="@drawable/right_selector"
        app:leftResources="@drawable/minus"
        app:rightResources="@drawable/plus"/>
```

### 3.在Activity或者Fragment中配置AddSubUtils

```java
 XShopCartBtn xShopCartBtn = (XShopCartBtn) findViewById(R.id.add_sub);
        addSubUtils.setBuyMax(30)       // 最大购买数，默认为int的最大值
                .setInventory(50)       // 库存，默认为int的最大值
                .setCurrentNumber(5)    // 设置当前数，默认为1
                .setStep(5)             // 步长，默认为1
                .setBuyMin(2)           // 购买的最小值，默认为1
                .setOnWarnListener(new AddSubUtils.OnWarnListener() {
                    @Override
                    public void onWarningForInventory(int inventory) {
                        Toast.makeText(MainActivity.this, "当前库存:" + inventory, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onWarningForBuyMax(int max) {
                        Toast.makeText(MainActivity.this, "超过最大购买数:" + max, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onWarningForBuyMin(int min) {
                        Toast.makeText(MainActivity.this, "低于最小购买数:" + min, Toast.LENGTH_SHORT).show();
                    }
                });
```

### 4. 如果你是在ListView或者RecyclerView中使用：

```java
        holder.list_item_utils
                    .setStep(1)
                    .setBuyMax(30)
                    .setPosition(position)    // 传入当前位置，一定要传，不然数据会错乱
                    .setCurrentNumber(dataBean.getValue())
                    .setInventory(50)
                    .setOnWarnListener(new OnWarnListener() {
                        @Override
                        public void onWarningForInventory(int inventory) {
                            Toast.makeText(ListViewActivity.this, "不能超过当前库存:" + inventory, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onWarningForBuyMax(int max) {
                            Toast.makeText(ListViewActivity.this, "超过最大购买数:" + max, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onWarningForBuyMin(int min) {
                            Toast.makeText(ListViewActivity.this, "低于最小购买数:" + min, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setOnChangeValueListener(new OnChangeValueListener() {
                        @Override
                        public void onChangeValue(int value,int position) {
                            setValue(position,value);    // 使用传回来的position设置数据
                        }
                    });
```

注意：这里 `setPosition()` 是一定要有的

采用的是链式调用，这里你只需要传入你关心的值即可。

## Author

👤 **许朋友爱玩**

- `Github` ： https://github.com/LoveLifeEveryday/
- 个人博客：http://xcynice.xyz/
- 掘金：https://juejin.im/user/5e429bbc5188254967066d1b/posts

## Show your support

Give a ⭐️ if this project helped you!

求星星⭐️，求赞👍

