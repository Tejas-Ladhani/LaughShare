# Custom Dialog
## Steps :
### 1.  Create Custom View/Layout in Res/layout
   
   ```css
      <androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <ImageView
        android:layout_width="0dp"
        android:layout_height="0dp"
        android:contentDescription="@string/no_connection_icon"
        android:src="@drawable/no_connection"
        app:layout_constraintBottom_toBottomOf="@id/guideline1"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="@id/guideline" />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="16dp"
        android:text="@string/no_internet"
        android:textColor="#EF6C00"
        android:textSize="24sp"
        android:textStyle="bold"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="@id/guideline1" />

    <androidx.constraintlayout.widget.Guideline
        android:id="@+id/guideline"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        app:layout_constraintGuide_percent="0.15" />

    <androidx.constraintlayout.widget.Guideline
        android:id="@+id/guideline1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        app:layout_constraintGuide_percent="0.75" />


</androidx.constraintlayout.widget.ConstraintLayout
 ```

### 2. Set the custom view as the view for dialog, using LayoutInflater

(In MainActivity.java)

```Java
private void showDialog() {
        //crete &Show  Alert Dialog
        
        LayoutInflater inflater = getLayoutInflater();
       
       AlertDialog.Builder builder =
                new AlertDialog.Builder(MainActivity.this).setView(inflater.inflate(R.layout.dialog_view, null)).
                        setPositiveButton("RETRY", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                loadMeme();
                            }
                        });
        builder.create().show();
    }
```

## Output :


