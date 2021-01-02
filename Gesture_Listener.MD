# Gesture Listener

## 1. Declare variable of type GestureDetectorCompat  
```java
  private GestureDetectorCompat mGestureDetectorCompat;
```
## 2. Initialize the variable
```java
mGestureDetectorCompat = new GestureDetectorCompat(this, new GestureDetector.SimpleOnGestureListener())
```
## 3. Override the methods
```java
           mGestureDetectorCompat = new GestureDetectorCompat(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if ((e1.getX() > e2.getX())) { //load meme when swiped R2L
                    loadMeme();
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
```

### NOTE: You can override other methods as well.

## 4. Override the OnTouchEvent() in the main activity
```java
@Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
```
