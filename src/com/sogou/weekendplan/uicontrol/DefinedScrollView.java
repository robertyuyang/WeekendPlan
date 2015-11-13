package com.sogou.weekendplan.uicontrol; 
  
  
import android.content.Context; 
import android.util.AttributeSet; 
import android.view.MotionEvent; 
import android.view.VelocityTracker; 
import android.view.View; 
import android.view.ViewConfiguration; 
import android.view.ViewGroup; 
import android.view.View.MeasureSpec; 
import android.widget.Scroller; 
  
/** 
* �Զ���һ��ScrollViewʵ����Ļ�����»��� 
*/
public class DefinedScrollView extends ViewGroup { 
	// ����ƽ�����ɸ���ҳ��֮����л�,��Ҫ����ƽ���Ĺ�������Ҫ��д��ComputeScroll���� 
	private Scroller mScroller; 
	// ��������û������ƣ�����Ҳ����GestureDetector 
	private VelocityTracker mVelocityTracker; 
	  
	// ��ǰ����Ļ���� 
	private int mCurrentScreen; 
	// ������ҳ�� 
	//private int mPage = 0; 
	// Ĭ�ϵ���Ļ���� 
	private int mDefaultScreen = 0; 
	  
	// ��Ļ����ʱ 
	private static final int TOUCH_STATE_REST = 0; 
	// ��Ļ����ʱ 
	private static final int TOUCH_STATE_SCROLLING = 1; 
	  
	// �����ٶ� 
	private static final int SNAP_VELOCITY = 600; 
	// ����״̬Ϊ��ǰ��Ļ���� 
	private int mTouchState = TOUCH_STATE_REST; 
	  
	// ������� 
	private int mTouchSlop; 
/*	// ���ĺ����������� 
	private float mLastMotionX; */
	// ���������������� 
	private float mLastMotionY; 
	  
	private PageListener pageListener; 
  
	/** 
	* �ڹ��췽���г�ʼ������״̬ 
	*  
	* @param context 
	* @param attrs 
	* @param defStyle 
	* ScrollView��Ĭ����ʽ 
	*/
	public DefinedScrollView(Context context, AttributeSet attrs, int defStyle) { 
		super(context, attrs, defStyle); 
		mScroller = new Scroller(context); 
		// ��ǰ��Ļ��ΪĬ�ϵ���Ļ����Ϊ��һ�� 
		mCurrentScreen = mDefaultScreen; 
		// ��һ�����룬��ʾ������ʱ���ֵ��ƶ�Ҫ�����������ſ�ʼ�ƶ��ؼ� 
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop(); 
	} 
  
	public DefinedScrollView(Context context, AttributeSet attrs) { 
	// 0��ʾû�з�� 
		this(context, attrs, 0); 
	} 
  
	/** 
	* ���򻭳�ÿһ����view���������õ���view�ĸ�����Ļ��һ�£����ΪgetChildCount()-1����Ļ��ȵ�view�� 
	*/
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) { 
		///int childLeft = 0; 
		int childTop = 0;
		int childCount = getChildCount(); 
		for (int i = 0; i < childCount; i++) { 
			final View childView = getChildAt(i); 
			if (childView.getVisibility() != View.GONE) { 
				////final int childWidth = childView.getMeasuredWidth(); 
				final int childHeight = childView.getMeasuredHeight();
/*				childView.layout(childLeft, 0, childLeft + childWidth, 
				childView.getMeasuredHeight()); */
				childView.layout(0, childTop, childView.getMeasuredHeight(), 
						childTop + childHeight); 
				childTop +=childHeight;
				///childLeft += childWidth; 
			} 
		} 
	}  
  
	/** 
	* onMeasure�����ڿؼ��ĸ�Ԫ����Ҫ���������ӿؼ�ʱ����.������һ�����⣬������Ҫ�ö��ط���������Ȼ����������������widthMeasureSpec��heightMeasureSpec. 
	* �������setMeasuredDimension����,���򵱿ؼ�����ʱ������һ������ʱ�쳣�� 
	*  
	*setMeasuredDimension(measuredHeight, measuredWidth); 
	  
	*  
	* ����������width��heightΪfill_parentʱ�������ڲ���ʱ������view��measure���������ģʽ��EXACTLY����Ϊ��view��ռ��ʣ�������Ŀռ䣬��������С��ȷ���ġ� 
	* ��������Ϊwrap_contentʱ����������ȥ����AT_MOST, ��ʾ��view�Ĵ�С����Ƕ��٣�������view�������������������Լ��ĳߴ硣 
	* ����view�Ĵ�С����Ϊ��ȷֵʱ�������������EXACTLY, ��MeasureSpec��UNSPECIFIEDģʽĿǰ��û�з�����ʲô�����ʹ�á�  
	*/
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) { 
		super.onMeasure(widthMeasureSpec, heightMeasureSpec); 
		/////final int width = MeasureSpec.getSize(widthMeasureSpec); 
		final int height = MeasureSpec.getSize(heightMeasureSpec); 
		final int widthMode = MeasureSpec.getMode(widthMeasureSpec); 
		if (widthMode != MeasureSpec.EXACTLY) { 
			throw new IllegalStateException("ScrollLayout only canmCurScreen run at EXACTLY mode!"); 
		} 
	  
		/** 
		* wrap_content ����ȥ����AT_MOST ����������ߴ� 
		* �̶���ֵ��fill_parent �����ģʽ��EXACTLY ������Ǿ�ȷ�ĳߴ� 
		*/
		final int heightMode = MeasureSpec.getMode(heightMeasureSpec); 
		if (heightMode != MeasureSpec.EXACTLY) { 
			throw new IllegalStateException("ScrollLayout only can run at EXACTLY mode!"); 
		} 
	  
		// The children are given the same width and height as the scrollLayout 
		final int count = getChildCount(); 
		for (int i = 0; i < count; i++) { 
			getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec); 
		} 
		//�ڵ�ǰ��ͼ����ƫ����(x , y)���괦������ʾ(����)����λ��(x , y)���괦�� 
		//��View��Content��λ���ƶ���(x,y)����View�Ĵ�С��λ�ò������ı䡣���Content������View�ķ�Χ���򳬳��Ĳ��ֻᱻ��ס�� 
		////scrollTo(mCurrentScreen * width, 0); 
		scrollTo(0,mCurrentScreen*height); 
	} 
  
	/** 
	* According to the position of current layout scroll to the destination 
	* page. 
	* �Ǵ�����Ļ�϶���һ��λ�����ֺ�Ĵ��� 
	*/
	public void snapToDestination() { 
		////final int screenWidth = getWidth(); 
		final int screenHeight = getHeight();
		////final int destScreen = (getScrollX() + screenWidth / 2) / screenWidth; 
		final int destScreen = (getScrollY() + screenHeight / 2) / screenHeight; 
		//���ݵ�ǰx����λ��ȷ���л����ڼ���  
		snapToScreen(destScreen); 
	} 
  
	/** 
	* ��ק��Ŀ����Ļ������ 
	* ���ڸ���ָ����Ļ���л�������Ļ 
	* @param whichScreen 
	*/
	public void snapToScreen(int whichScreen) { 
		// get the valid layout page 
		whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1)); 
		///if (getScrollX() != (whichScreen * getWidth())) { 
		if (getScrollY() != (whichScreen * getHeight())) { 	  
			final int delta = whichScreen * getHeight() - getScrollY(); 
			///mScroller.startScroll(getScrollX(), 0, delta, 0, Math.abs(delta) * 2); 
			mScroller.startScroll(0,getScrollY(), 0,delta,  Math.abs(delta) * 2); 
			mCurrentScreen = whichScreen; 
			if(mCurrentScreen>Configure.curentPage){ 
				Configure.curentPage = whichScreen; 
				pageListener.page(Configure.curentPage); 
			}else if(mCurrentScreen<Configure.curentPage){ 
				Configure.curentPage = whichScreen; 
				pageListener.page(Configure.curentPage); 
			} 
			invalidate(); // Redraw the layout 
		} 
	} 
  
	/** 
	* ��õ�ǰҳ�� 
	*/
	public int getCurScreen() { 
		return mCurrentScreen; 
	} 
	  
	/** 
	* ��������ĵ�ǰҳ�� 
	*/ 
	public int getPage() { 
		return Configure.curentPage; 
	} 
	  
	  
	public void setToScreen(int whichScreen) { 
		whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1)); 
		mCurrentScreen = whichScreen; 
		///scrollTo(whichScreen * getWidth(), 0); 
		scrollTo(0,whichScreen * getHeight() ); 
	} 
  
	/** 
	* ��Ҫ�����Ǽ����϶���λ���������±���������Ҫ��ʾ����Ļ(setCurrentScreen(mCurrentScreen);)�� 
	*  
	*  
	*��Ҫʱ�ɸ��ؼ����������֪ͨ��һ���ӽڵ���Ҫ��������mScrollX��mScrollY��ֵ�� 
	*���͵����Ӿ�����һ���ӽڵ�����ʹ��Scroller���л�������ʱ���ᱻִ�С� 
	*���ԣ��Ӹ÷�����ע���������̳���������Ļ�һ�㶼����Scroller������֡� 
	*/ 
	@Override
	public void computeScroll() { 
		//�������ж϶����Ƿ���ɣ����û����ɷ���true����ִ�н���ˢ�µĲ���������λ����Ϣ�������¼����������»�������״̬�Ľ��档 
		/** 
		* �������û�����(mScroller.computeScrollOffset() == true)��ô��ʹ��scrollTo������mScrollX��mScrollY��ֵ�������¼���ˢ�½��棬 
		  
		����postInvalidate()�������»��ƽ��棬 
		  
		postInvalidate()���������invalidate()������ 
		  
		invalidate()�����ֻ����computeScroll������ 
		  
		�������ܶ���ʼ���໥���ã�ֱ��mScroller.computeScrollOffset() ����false�Ż�ֹͣ������ػ涯�� 
		*/
		if (mScroller.computeScrollOffset()) { 
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY()); 
			postInvalidate(); 
		} 
	} 
  
	/** 
	* �����ֻ���Ļ�Ĵ����¼� 
	* @event ����eventΪ�ֻ���Ļ�����¼���װ��Ķ������з�װ�˸��¼���������Ϣ�����紥����λ�á������������Լ�������ʱ��ȡ��ö�������û������ֻ���Ļʱ�������� 
	* <A href='\"http://www.eoeandroid.com/home.php?mod=space&uid=7300\"' target='\"_blank\"'>@return</A> �÷����ķ���ֵ�����������Ӧ�¼�����ͬ��ͬ���ǵ��Ѿ������ش����˸��¼��Ҳ�ϣ�������ص������ٴδ���ʱ����true�����򷵻�false 
	* ��Ļ�����£�����Ļ������ʱ�����Զ����ø÷����������¼�����ʱMotionEvent.getAction()��ֵΪMotionEvent.ACTION_DOWN�������Ӧ�ó�������Ҫ������Ļ�����µ��¼���ֻ�����¸ûص�������Ȼ���ڷ����н��ж������жϼ��ɡ�  
	* ��Ļ��̧�𣺵����ر��뿪��Ļʱ�������¼������¼�ͬ����ҪonTouchEvent��������׽��Ȼ���ڷ����н��ж����жϡ���MotionEvent.getAction()��ֵΪMotionEvent.ACTION_UPʱ����ʾ����Ļ��̧����¼��� 
	* ����Ļ���϶����÷������������ر�����Ļ�ϻ������¼���ͬ���ǵ���MotionEvent.getAction()�������ж϶���ֵ�Ƿ�ΪMotionEvent.ACTION_MOVE�ٽ��д��� 
	*/
	@Override
	public boolean onTouchEvent(MotionEvent event) { 
	  
		if (mVelocityTracker == null) { 
		//����׷�ٴ����¼���flinging�¼������������¼��������ʡ���obtain()������������ʵ�� 
		mVelocityTracker = VelocityTracker.obtain(); 
		} 
		//��addMovement(MotionEvent)������motion event���뵽VelocityTracker��ʵ���� 
		mVelocityTracker.addMovement(event); 
		  
		final int action = event.getAction(); 
		////final float x = event.getX(); 
		final float y = event.getY(); 
	  
		switch (action) { 
			case MotionEvent.ACTION_DOWN: 
				if (!mScroller.isFinished()) { 
					mScroller.abortAnimation(); 
				} 
				///mLastMotionX = x; 
				mLastMotionY = y; 
			break; 
			case MotionEvent.ACTION_MOVE: 
				///int deltaX = (int) (mLastMotionX - x); 
				///mLastMotionX = x; 
				int deltaY = (int) (mLastMotionY - y); 
				mLastMotionY = y; 
				//�ڵ�ǰ��ͼ���ݼ���ƫ��(x , y)����λ����ʾ(����)����Ҳ����ƫ��(x,y)����λ 
				///scrollBy(deltaX, 0); 
				scrollBy(0, deltaY); 
			break; 
			case MotionEvent.ACTION_UP: 
				final VelocityTracker velocityTracker = mVelocityTracker; 
					//����ʹ�õ�����ʱ��ʹ��computeCurrentVelocity(int)��ʼ�����ʵĵ�λ������õ�ǰ���¼������ʣ� 
					//Ȼ��ʹ��getXVelocity() ��getXVelocity()��ú������������ʡ�  
					//1000:��ʹ�õ����ʵ�λ.1����˼�ǣ���һ�����˶��˶��ٸ����ص����ʣ� 1000��ʾ һ��ʱ�����˶��˶��ٸ����ء�  
					velocityTracker.computeCurrentVelocity(1000); 
					///int velocityX = (int) velocityTracker.getXVelocity(); 
					int velocityY = (int) velocityTracker.getYVelocity();
					//�����ָ��������>600���ҵ�ǰ��Ļ�л�����Ϊ��ǰ��Ļ��ʼ����ʱ���ǵ�һ����Ϊ0 
					///if (velocityX > SNAP_VELOCITY && getCurScreen() > 0) { 
					if (velocityY > SNAP_VELOCITY && getCurScreen() > 0) { 
					// Fling enough to move left 
					snapToScreen(getCurScreen() - 1); 
					//--Configure.curentPage; 
					pageListener.page(Configure.curentPage); 
				} ///else if (velocityX < -SNAP_VELOCITY && getCurScreen() < getChildCount() - 1) { 
				else if (velocityY < -SNAP_VELOCITY && getCurScreen() < getChildCount() - 1) { 
					// Fling enough to move right 
					snapToScreen(getCurScreen() + 1); 
					//++Configure.curentPage; 
					//pageListener.page(Configure.curentPage); 
				} else { 
					snapToDestination(); 
				} 
				if (mVelocityTracker != null) { 
					mVelocityTracker.recycle(); 
					mVelocityTracker = null; 
				} 
				mTouchState = TOUCH_STATE_REST; 
			break; 
			case MotionEvent.ACTION_CANCEL: 
				mTouchState = TOUCH_STATE_REST; 
			break; 
		} 
		return true; 
	} 
	  
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) { 
		if(Configure.isMove) return false;//���طַ����ӿؼ� 
		final int action = ev.getAction(); 
		if ((action == MotionEvent.ACTION_MOVE) && (mTouchState != TOUCH_STATE_REST)) { 
			return true; 
		} 
	  
		///final float x = ev.getX(); 
		final float y = ev.getY(); 
		  
		switch (action) { 
		case MotionEvent.ACTION_MOVE: 
			////final int xDiff = (int) Math.abs(mLastMotionX - x); 
			final int yDiff = (int) Math.abs(mLastMotionY - y); 
			////if (xDiff > mTouchSlop) { 
			if (yDiff > mTouchSlop) {
				mTouchState = TOUCH_STATE_SCROLLING; 
			} 
			break; 
		case MotionEvent.ACTION_DOWN: 
			///mLastMotionX = x; 
			mLastMotionY = y; 
			mTouchState = mScroller.isFinished() ? TOUCH_STATE_REST : TOUCH_STATE_SCROLLING; 
			break; 
		case MotionEvent.ACTION_CANCEL: 
		case MotionEvent.ACTION_UP: 
			mTouchState = TOUCH_STATE_REST; 
			break; 
		} 
		return mTouchState != TOUCH_STATE_REST; 
	} 
	  
	public void setPageListener(PageListener pageListener) { 
		this.pageListener = pageListener; 
	} 
	  
	public interface PageListener { 
		void page(int page); 
	} 
}