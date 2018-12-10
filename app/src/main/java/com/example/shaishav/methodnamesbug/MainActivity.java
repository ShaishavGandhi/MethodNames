package com.example.shaishav.methodnamesbug;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Simple observable declaration.
    Observable<Integer> observable = Observable.just(1);
    try {
      // Calls the `subscribeWith` method, but the lint check does not detect it and
      // let's it pass when you run `./gradlew :app:lint`
      Observer<Integer> observer = getDisposable(observable::subscribeWith);
    } catch (Exception e ){

    }

    // Uncomment the following block and then run `./gradlew :app:lint`. This will fail the
    // build because of the `SubscribeDetector` that we've registered. However, the `subscribeWith`
    // above is not caught.

//    Observable.just(1)
//        .subscribeWith(new Observer<Integer>() {
//          @Override
//          public void onSubscribe(Disposable d) {
//
//          }
//
//          @Override
//          public void onNext(Integer integer) {
//
//          }
//
//          @Override
//          public void onError(Throwable e) {
//
//          }
//
//          @Override
//          public void onComplete() {
//
//          }
//        });
  }

  Observer<Integer> getDisposable(Function<Observer<Integer>, Observer<Integer>> func) throws Exception {
    return func.apply(new Observer<Integer>() {
      @Override
      public void onSubscribe(Disposable d) {

      }

      @Override
      public void onNext(Integer integer) {

      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onComplete() {

      }
    });
  }
}
