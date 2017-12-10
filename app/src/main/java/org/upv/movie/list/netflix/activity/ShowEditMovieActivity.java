package org.upv.movie.list.netflix.activity;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.gson.Gson;

import org.upv.movie.list.netflix.R;
import org.upv.movie.list.netflix.model.User;
import org.upv.movie.list.netflix.utils.DownloadImageTask;
import org.upv.movie.list.netflix.model.Movie;
import org.upv.movie.list.netflix.movie.MovieList;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.upv.movie.list.netflix.activity.PerfilActivity.USERS;
import static org.upv.movie.list.netflix.activity.PerfilActivity.USERS_KEY_USERS;
import static org.upv.movie.list.netflix.activity.PerfilActivity.USER_LOGIN_PREFERENCES;
import static org.upv.movie.list.netflix.activity.PerfilActivity.USER_LOGIN_PREFERENCES_KEY_USER;

/**
 * Created by jvg63 on 09/11/2017.
 */

public class ShowEditMovieActivity extends AppCompatActivity {

    public static String PARAM_EXTRA_ID_PELICULA = "ID";
    private ImageView photo;
    private EditText title, category, summary, directors, actors, producers, studio, comment;
    private RatingBar rating;
    private Button showComments, pushComment;
    private Float userRating;
    private Set<String> userList;
    private Set movieRatings;
    private User user;
    private Movie movie;
    private VideoView myVideoView;
    private ImageButton btnPlayPause;
    private int position = 0;
    private ProgressDialog progressDialog;
    private MediaController mediaControls;
    private boolean rated = false;

    //  Admob Intersticial
    private InterstitialAd interstitialAd;

    // Admob Video Bonificado
    private RewardedVideoAd ad;

    private View main_container;
    private Snackbar msg = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_edit_movie);

        main_container = findViewById(R.id.main_container);

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(MainActivityApplication.ADMOD_ID_INTERSTICIAL);
        interstitialAd.loadAd(new AdRequest.Builder()
//                .addTestDevice("ID_DISPOSITIVO_FISICO_TEST")
                .build());

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
//                interstitialAd.loadAd(new AdRequest.Builder()
////                        .addTestDevice("ID_BLOQUE_ANUNCIOS_INTERSTICIAL")
//                        .build());
            }

            public void onAdLoaded() {
                interstitialAd.show();
            }
        });


        ad = MobileAds.getRewardedVideoAdInstance(this);
        ad.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {
//                Toast.makeText(ShowEditMovieActivity.this, "Vídeo Bonificado cargado", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoAdOpened() {
            }

            @Override
            public void onRewardedVideoStarted() {
            }

            @Override
            public void onRewardedVideoAdClosed() {
                ad.loadAd(MainActivityApplication.ADMOD_ID_BONIFICADO, new AdRequest.Builder()
//                        .addTestDevice("ID_DISPOSITIVO_FISICO_TEST")
                        .build());
            }

            @Override
            public void onRewarded(RewardItem rewardItem) {

                saveNoMoreRewardedVideo();
                if (msg != null) {
                    msg.dismiss();
                }

                msg = Snackbar.make(main_container, "Ya no se mostrarán más anuncios por su fidelidad", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        msg.dismiss();
                    }
                });

                msg.show();
//                Toast.makeText(ShowEditMovieActivity.this, "onRewarded: moneda virtual: " + rewardItem.getType() + "  aumento: " + rewardItem.getAmount(), Toast.LENGTH_SHORT).show();
//
            }

            @Override
            public void onRewardedVideoAdLeftApplication() {
            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
            }
        });

        ad.loadAd(MainActivityApplication.ADMOD_ID_BONIFICADO, new AdRequest.Builder()
//                .addTestDevice("ID_DISPOSITIVO_FISICO_TEST")
                .build());

        photo = findViewById(R.id.photo);
        title = findViewById(R.id.title);
        category = findViewById(R.id.category);
        summary = findViewById(R.id.summary);
        actors = findViewById(R.id.actor);
        directors = findViewById(R.id.director);
        producers = findViewById(R.id.producer);
        studio = findViewById(R.id.studio);
        rating = findViewById(R.id.ratingBar);
        showComments = findViewById(R.id.buttonShowComments);
        pushComment = findViewById(R.id.buttonComment);
        comment = findViewById(R.id.comment);
        myVideoView = findViewById(R.id.video_view);
        btnPlayPause = findViewById(R.id.btn_play_pause);

        Intent data = getIntent();
        int id = -1;

        if (data != null && data.getExtras() != null) {
            id = data.getExtras().getInt(PARAM_EXTRA_ID_PELICULA, -1);
        }

        if (id == -1) {
            // Mode Edit
        } else {
            // Mode View
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                postponeEnterTransition();
            }
            mostrarPelicula(id);
            cargaVideo(id);
        }
    }

    private void saveNoMoreRewardedVideo() {
        rewardUser();
    }

    private void scheduleStartPostponedTransition(final View sharedElement) {
        sharedElement.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        sharedElement.getViewTreeObserver().removeOnPreDrawListener(this);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startPostponedEnterTransition();
                        }
                        return true;
                    }
                });
    }

    private void mostrarPelicula(final int id) {
        movie = MovieList.list.get(id);
        title.setText(movie.getTitle());
        category.setText(movie.getCategory());
        summary.setText(movie.getDescription());
        studio.setText(movie.getStudio());
        directors.setText(movie.getDirectors());
        actors.setText(movie.getActors());
        producers.setText(movie.getProducers());

        //Se comprueba si el usuario ha valorado
        user = readUserFromPreferences();

        String[] ratingComment = user.getRating(movie.getId()).split("╩");
        rating.setRating(Float.parseFloat(ratingComment[0]));
        comment.setText(ratingComment[1]);

        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                userRating = rating;
            }
        });

        pushComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userRating != null) {
                    user.setRating(id, userRating, comment.getText().toString());
                    writeUserToPreferences(user);
                    writeRatingToPreferences(id);
                    rated = true;
                    showAllComments(id);
                } else {
                    Toast.makeText(getApplication(), R.string.ASEM_add_comment_rating, Toast.LENGTH_SHORT).show();
                }
            }
        });

        showComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAllComments(id);
            }
        });

        new DownloadImageTask(photo).execute(movie.getBackgroundImageUrl());
        protectFields();
        Transition lista_enter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            lista_enter = TransitionInflater.from(this).inflateTransition(R.transition.transition_curva);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setSharedElementEnterTransition(lista_enter);
        }
        scheduleStartPostponedTransition(photo);
    }

    private void cargaVideo(int id) {
        //set the media controller buttons
        /*if (mediaControls == null) {
            mediaControls = new MediaController(ShowEditMovieActivity.this);
        }*/

        btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!user.isUserRewarded() && ad.isLoaded()) {
                    ad.show();
                }
                // create a progress bar while the video file is loading
                progressDialog = new ProgressDialog(ShowEditMovieActivity.this);
                // set a message for the progress bar
                progressDialog.setMessage("Loading...");
                //set the progress bar not cancelable on users' touch
                progressDialog.setCanceledOnTouchOutside(false);
                // show the progress bar
                progressDialog.show();

                try {
                    if (!myVideoView.isPlaying()) {
                        //set the media controller in the VideoView
                        myVideoView.setMediaController(mediaControls);
                        //set the uri of the video to be played
                        myVideoView.setVideoPath(movie.getVideoUrl());
                        myVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                btnPlayPause.setImageResource(R.drawable.ic_play);
                            }
                        });
                    } else {
                        progressDialog.dismiss();
                        myVideoView.pause();
                        btnPlayPause.setImageResource(R.drawable.ic_play);
                    }
                } catch (Exception e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }
                myVideoView.requestFocus();
                myVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        progressDialog.dismiss();
                        mediaPlayer.setLooping(true);
                        btnPlayPause.setImageResource(R.drawable.ic_pause);
                        myVideoView.start();
                    }
                });
            }
        });
    }

    private void protectFields() {
        photo.setFocusable(false);
        title.setFocusable(false);
        studio.setFocusable(false);
        category.setFocusable(false);
        summary.setFocusable(false);
        actors.setFocusable(false);
        directors.setFocusable(false);
        producers.setFocusable(false);
    }

    public User readUserFromPreferences() {
        User user = null;
        SharedPreferences prefsLogin = getSharedPreferences(USER_LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        String userLogged = prefsLogin.getString(USER_LOGIN_PREFERENCES_KEY_USER, "");
        SharedPreferences prefs = getSharedPreferences(USERS, Context.MODE_PRIVATE);
        userList = prefs.getStringSet("users", userList);
        Gson gson = new Gson();

        for (String anUserList : userList) {
            User userAux = gson.fromJson(anUserList, User.class);
            if (userLogged.equals(userAux.getUsername())) {
                user = userAux;
                break;
            }
        }
        return user;
    }

    public void writeUserToPreferences(User user) {
        SharedPreferences prefs = getSharedPreferences("Usuarios", Context.MODE_PRIVATE);
        userList = prefs.getStringSet("users", userList);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        Set newStrSet = new HashSet<User>();
        for (String anUserList : userList) {
            User userAux = gson.fromJson(anUserList, User.class);
            if (!userAux.getUsername().equals(user.getUsername())) {
                newStrSet.add(anUserList);
            }
        }
        String json = gson.toJson(user);
        newStrSet.add(json);
        editor.putStringSet("users", newStrSet);
        editor.apply();
    }

    private void writeRatingToPreferences(int id) {
        SharedPreferences prefs = getSharedPreferences("Valoraciones", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        movieRatings = prefs.getStringSet("ratings", movieRatings);
        if (movieRatings == null) {
            movieRatings = new HashSet<String>();
        }
        Gson gson = new Gson();
        String json = gson.toJson(id + "╩" + userRating + "╩" + user.getUsername());

        //make a copy, update it and save it
        Set oldSet = prefs.getStringSet("ratings", movieRatings);
        Set<String> newStrSet = new HashSet<>();
        newStrSet.add(json);

        movie.clearRatings();
        movie.addRating(userRating);

        for (Object clave : oldSet) {
            String claveAux = gson.fromJson((String) clave, String.class);

            String[] rating = claveAux.split("╩");

            if (id != Integer.parseInt(rating[0]) && !user.getUsername().equals(rating[2])) {
                newStrSet.add((String) clave);
                movie.addRating(Float.parseFloat(rating[1]));
            }
        }
        editor.putStringSet("ratings", newStrSet);
        editor.apply();
    }

    private void showAllComments(int id) {
        Intent intent = new Intent(getApplication(), Ratings.class);
        intent.putExtra("idPelicula", id);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        } else {
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        if (rated)
            setResult(RESULT_OK);
        super.onBackPressed();
    }




    public void rewardUser() {
        User userAux = null;

        SharedPreferences prefsLogin = getSharedPreferences(USER_LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        String userLogged = prefsLogin.getString(USER_LOGIN_PREFERENCES_KEY_USER, "");

        SharedPreferences prefs = getSharedPreferences(USERS, Context.MODE_PRIVATE);
        userList = prefs.getStringSet(USERS_KEY_USERS, null);

        Gson gson = new Gson();
        Iterator<String> userListIterator = this.userList.iterator();

        while (userListIterator.hasNext()) {
            userAux = gson.fromJson(userListIterator.next(), User.class);
            if (userLogged.equals(userAux.getUsername())) {
                user = userAux;
                break;
            }
        }

        user.setUserRewarded(true);


        Set userListNew = new HashSet<>();

        userListIterator = userList.iterator();

            while (userListIterator.hasNext()) {
                userAux = gson.fromJson(userListIterator.next(), User.class);
                if (user.getUsername().equals(userAux.getUsername())) {
                    String json = gson.toJson(user);
                    userListNew.add(json);
                } else {
                    String json = gson.toJson(userAux);
                    userListNew.add(json);
                }
            }

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(USERS_KEY_USERS, userListNew);
        editor.commit();
    }
}
