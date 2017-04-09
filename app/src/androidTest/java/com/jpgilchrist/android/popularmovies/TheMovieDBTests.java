package com.jpgilchrist.android.popularmovies;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jpgilchrist.android.popularmovies.tmdb.TMDBPage;
import com.jpgilchrist.android.popularmovies.tmdb.TMDBUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class TheMovieDBTests {

    private static final String TAG = TheMovieDBTests.class.getSimpleName();

    private static final String API_KEY = BuildConfig.TMDB_API_KEY;
    private static final String POPULAR_URL = "https://api.themoviedb.org/3/movie/popular?api_key=" + API_KEY + "&language=en-US&page=1";
    private static final String TOPRATED_URL = "https://api.themoviedb.org/3/movie/top_rated?api_key=" + API_KEY + "&language=en-US&page=2";

    /*
     * these tests need to run with android since Uri is an android specific library
     */

    @Test
    public void testPopularUrl() throws Exception {
        assertEquals(new URL(POPULAR_URL), TMDBUtils.buildUrl(1, TMDBUtils.Sort.POPULAR));
    }

    @Test
    public void testTopRatedUrl() throws Exception {
        assertEquals(new URL(TOPRATED_URL), TMDBUtils.buildUrl(2, TMDBUtils.Sort.TOP_RATED));
    }

    @Test
    public void testFetchPopularMovies() throws Exception {
        TMDBPage response = TMDBUtils.getResponseFromURL(TMDBUtils.buildUrl(1, TMDBUtils.Sort.POPULAR));

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String expected = gson.toJson(gson.fromJson(POPULAR_MOVIE_RESPONSE, TMDBPage.class));
        String actual = gson.toJson(response);

        Log.d(TAG, actual);

        assertEquals(expected, actual);
    }

    private static final String POPULAR_MOVIE_RESPONSE = "{\n" +
            "    \"page\": 1,\n" +
            "    \"results\": [\n" +
            "        {\n" +
            "            \"poster_path\": \"/tnmL0g604PDRJwGJ5fsUSYKFo9.jpg\",\n" +
            "            \"adult\": false,\n" +
            "            \"overview\": \"A live-action adaptation of Disney's version of the classic 'Beauty and the Beast' tale of a cursed prince and a beautiful young woman who helps him break the spell.\",\n" +
            "            \"release_date\": \"2017-03-15\",\n" +
            "            \"genre_ids\": [\n" +
            "                14,\n" +
            "                10749\n" +
            "            ],\n" +
            "            \"id\": 321612,\n" +
            "            \"original_title\": \"Beauty and the Beast\",\n" +
            "            \"original_language\": \"en\",\n" +
            "            \"title\": \"Beauty and the Beast\",\n" +
            "            \"backdrop_path\": \"/6aUWe0GSl69wMTSWWexsorMIvwU.jpg\",\n" +
            "            \"popularity\": 166.765146,\n" +
            "            \"vote_count\": 815,\n" +
            "            \"video\": false,\n" +
            "            \"vote_average\": 7.2\n" +
            "        },\n" +
            "        {\n" +
            "            \"poster_path\": \"/45Y1G5FEgttPAwjTYic6czC9xCn.jpg\",\n" +
            "            \"adult\": false,\n" +
            "            \"overview\": \"In the near future, a weary Logan cares for an ailing Professor X in a hide out on the Mexican border. But Logan's attempts to hide from the world and his legacy are up-ended when a young mutant arrives, being pursued by dark forces.\",\n" +
            "            \"release_date\": \"2017-02-28\",\n" +
            "            \"genre_ids\": [\n" +
            "                28,\n" +
            "                18,\n" +
            "                878\n" +
            "            ],\n" +
            "            \"id\": 263115,\n" +
            "            \"original_title\": \"Logan\",\n" +
            "            \"original_language\": \"en\",\n" +
            "            \"title\": \"Logan\",\n" +
            "            \"backdrop_path\": \"/5pAGnkFYSsFJ99ZxDIYnhQbQFXs.jpg\",\n" +
            "            \"popularity\": 150.3258,\n" +
            "            \"vote_count\": 1817,\n" +
            "            \"video\": false,\n" +
            "            \"vote_average\": 7.6\n" +
            "        },\n" +
            "        {\n" +
            "            \"poster_path\": \"/5z4Bj0zUupF7z4VW0REAdso1uxz.jpg\",\n" +
            "            \"adult\": false,\n" +
            "            \"overview\": \"A koala named Buster recruits his best friend to help him drum up business for his theater by hosting a singing competition.\",\n" +
            "            \"release_date\": \"2016-12-02\",\n" +
            "            \"genre_ids\": [\n" +
            "                16,\n" +
            "                35,\n" +
            "                18,\n" +
            "                10751,\n" +
            "                10402\n" +
            "            ],\n" +
            "            \"id\": 335797,\n" +
            "            \"original_title\": \"Sing\",\n" +
            "            \"original_language\": \"en\",\n" +
            "            \"title\": \"Sing\",\n" +
            "            \"backdrop_path\": \"/usJKCEMXV3tECgIJj8ZTEndmY2E.jpg\",\n" +
            "            \"popularity\": 85.627505,\n" +
            "            \"vote_count\": 890,\n" +
            "            \"video\": false,\n" +
            "            \"vote_average\": 6.7\n" +
            "        },\n" +
            "        {\n" +
            "            \"poster_path\": \"/aoUyphk4nwffrwlZRaOa0eijgpr.jpg\",\n" +
            "            \"adult\": false,\n" +
            "            \"overview\": \"Explore the mysterious and dangerous home of the king of the apes as a team of explorers ventures deep inside the treacherous, primordial island.\",\n" +
            "            \"release_date\": \"2017-03-08\",\n" +
            "            \"genre_ids\": [\n" +
            "                878,\n" +
            "                28,\n" +
            "                12,\n" +
            "                14\n" +
            "            ],\n" +
            "            \"id\": 293167,\n" +
            "            \"original_title\": \"Kong: Skull Island\",\n" +
            "            \"original_language\": \"en\",\n" +
            "            \"title\": \"Kong: Skull Island\",\n" +
            "            \"backdrop_path\": \"/pGwChWiAY1bdoxL79sXmaFBlYJH.jpg\",\n" +
            "            \"popularity\": 72.403489,\n" +
            "            \"vote_count\": 724,\n" +
            "            \"video\": false,\n" +
            "            \"vote_average\": 6.1\n" +
            "        },\n" +
            "        {\n" +
            "            \"poster_path\": \"/z09QAf8WbZncbitewNk6lKYMZsh.jpg\",\n" +
            "            \"adult\": false,\n" +
            "            \"overview\": \"Dory is reunited with her friends Nemo and Marlin in the search for answers about her past. What can she remember? Who are her parents? And where did she learn to speak Whale? ( Nick if you see this, no profanity please )\",\n" +
            "            \"release_date\": \"2016-06-16\",\n" +
            "            \"genre_ids\": [\n" +
            "                16,\n" +
            "                10751\n" +
            "            ],\n" +
            "            \"id\": 127380,\n" +
            "            \"original_title\": \"Finding Dory\",\n" +
            "            \"original_language\": \"en\",\n" +
            "            \"title\": \"Finding Dory\",\n" +
            "            \"backdrop_path\": \"/iWRKYHTFlsrxQtfQqFOQyceL83P.jpg\",\n" +
            "            \"popularity\": 58.554492,\n" +
            "            \"vote_count\": 2799,\n" +
            "            \"video\": false,\n" +
            "            \"vote_average\": 6.6\n" +
            "        },\n" +
            "        {\n" +
            "            \"poster_path\": \"/jjBgi2r5cRt36xF6iNUEhzscEcb.jpg\",\n" +
            "            \"adult\": false,\n" +
            "            \"overview\": \"Twenty-two years after the events of Jurassic Park, Isla Nublar now features a fully functioning dinosaur theme park, Jurassic World, as originally envisioned by John Hammond.\",\n" +
            "            \"release_date\": \"2015-06-09\",\n" +
            "            \"genre_ids\": [\n" +
            "                28,\n" +
            "                12,\n" +
            "                878,\n" +
            "                53\n" +
            "            ],\n" +
            "            \"id\": 135397,\n" +
            "            \"original_title\": \"Jurassic World\",\n" +
            "            \"original_language\": \"en\",\n" +
            "            \"title\": \"Jurassic World\",\n" +
            "            \"backdrop_path\": \"/dkMD5qlogeRMiEixC4YNPUvax2T.jpg\",\n" +
            "            \"popularity\": 52.420363,\n" +
            "            \"vote_count\": 6569,\n" +
            "            \"video\": false,\n" +
            "            \"vote_average\": 6.5\n" +
            "        },\n" +
            "        {\n" +
            "            \"poster_path\": \"/gri0DDxsERr6B2sOR1fGLxLpSLx.jpg\",\n" +
            "            \"adult\": false,\n" +
            "            \"overview\": \"In 1926, Newt Scamander arrives at the Magical Congress of the United States of America with a magically expanded briefcase, which houses a number of dangerous creatures and their habitats. When the creatures escape from the briefcase, it sends the American wizarding authorities after Newt, and threatens to strain even further the state of magical and non-magical relations.\",\n" +
            "            \"release_date\": \"2016-11-16\",\n" +
            "            \"genre_ids\": [\n" +
            "                28,\n" +
            "                12,\n" +
            "                14\n" +
            "            ],\n" +
            "            \"id\": 259316,\n" +
            "            \"original_title\": \"Fantastic Beasts and Where to Find Them\",\n" +
            "            \"original_language\": \"en\",\n" +
            "            \"title\": \"Fantastic Beasts and Where to Find Them\",\n" +
            "            \"backdrop_path\": \"/oLBqfSFsyriQ8D5faZSsdVnKzVl.jpg\",\n" +
            "            \"popularity\": 47.776854,\n" +
            "            \"vote_count\": 2920,\n" +
            "            \"video\": false,\n" +
            "            \"vote_average\": 7\n" +
            "        },\n" +
            "        {\n" +
            "            \"poster_path\": \"/qjiskwlV1qQzRCjpV0cL9pEMF9a.jpg\",\n" +
            "            \"adult\": false,\n" +
            "            \"overview\": \"A rogue band of resistance fighters unite for a mission to steal the Death Star plans and bring a new hope to the galaxy.\",\n" +
            "            \"release_date\": \"2016-12-14\",\n" +
            "            \"genre_ids\": [\n" +
            "                28,\n" +
            "                18,\n" +
            "                878,\n" +
            "                10752\n" +
            "            ],\n" +
            "            \"id\": 330459,\n" +
            "            \"original_title\": \"Rogue One: A Star Wars Story\",\n" +
            "            \"original_language\": \"en\",\n" +
            "            \"title\": \"Rogue One: A Star Wars Story\",\n" +
            "            \"backdrop_path\": \"/tZjVVIYXACV4IIIhXeIM59ytqwS.jpg\",\n" +
            "            \"popularity\": 39.447884,\n" +
            "            \"vote_count\": 2769,\n" +
            "            \"video\": false,\n" +
            "            \"vote_average\": 7.3\n" +
            "        },\n" +
            "        {\n" +
            "            \"poster_path\": \"/hLudzvGfpi6JlwUnsNhXwKKg4j.jpg\",\n" +
            "            \"adult\": false,\n" +
            "            \"overview\": \"Taking place after alien crafts land around the world, an expert linguist is recruited by the military to determine whether they come in peace or are a threat.\",\n" +
            "            \"release_date\": \"2016-11-10\",\n" +
            "            \"genre_ids\": [\n" +
            "                18,\n" +
            "                878\n" +
            "            ],\n" +
            "            \"id\": 329865,\n" +
            "            \"original_title\": \"Arrival\",\n" +
            "            \"original_language\": \"en\",\n" +
            "            \"title\": \"Arrival\",\n" +
            "            \"backdrop_path\": \"/yIZ1xendyqKvY3FGeeUYUd5X9Mm.jpg\",\n" +
            "            \"popularity\": 37.418927,\n" +
            "            \"vote_count\": 3134,\n" +
            "            \"video\": false,\n" +
            "            \"vote_average\": 6.9\n" +
            "        },\n" +
            "        {\n" +
            "            \"poster_path\": \"/rXMWOZiCt6eMX22jWuTOSdQ98bY.jpg\",\n" +
            "            \"adult\": false,\n" +
            "            \"overview\": \"Though Kevin has evidenced 23 personalities to his trusted psychiatrist, Dr. Fletcher, there remains one still submerged who is set to materialize and dominate all the others. Compelled to abduct three teenage girls led by the willful, observant Casey, Kevin reaches a war for survival among all of those contained within him—as well as everyone around him—as the walls between his compartments shatter apart.\",\n" +
            "            \"release_date\": \"2017-01-19\",\n" +
            "            \"genre_ids\": [\n" +
            "                27,\n" +
            "                53\n" +
            "            ],\n" +
            "            \"id\": 381288,\n" +
            "            \"original_title\": \"Split\",\n" +
            "            \"original_language\": \"en\",\n" +
            "            \"title\": \"Split\",\n" +
            "            \"backdrop_path\": \"/cEdgmtLA21b6qfte4zYSAAI5WW0.jpg\",\n" +
            "            \"popularity\": 36.21393,\n" +
            "            \"vote_count\": 1335,\n" +
            "            \"video\": false,\n" +
            "            \"vote_average\": 6.7\n" +
            "        },\n" +
            "        {\n" +
            "            \"poster_path\": \"/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg\",\n" +
            "            \"adult\": false,\n" +
            "            \"overview\": \"Interstellar chronicles the adventures of a group of explorers who make use of a newly discovered wormhole to surpass the limitations on human space travel and conquer the vast distances involved in an interstellar voyage.\",\n" +
            "            \"release_date\": \"2014-11-05\",\n" +
            "            \"genre_ids\": [\n" +
            "                12,\n" +
            "                18,\n" +
            "                878\n" +
            "            ],\n" +
            "            \"id\": 157336,\n" +
            "            \"original_title\": \"Interstellar\",\n" +
            "            \"original_language\": \"en\",\n" +
            "            \"title\": \"Interstellar\",\n" +
            "            \"backdrop_path\": \"/xu9zaAevzQ5nnrsXN6JcahLnG4i.jpg\",\n" +
            "            \"popularity\": 34.144245,\n" +
            "            \"vote_count\": 7842,\n" +
            "            \"video\": false,\n" +
            "            \"vote_average\": 8\n" +
            "        },\n" +
            "        {\n" +
            "            \"poster_path\": \"/kqjL17yufvn9OVLyXYpvtyrFfak.jpg\",\n" +
            "            \"adult\": false,\n" +
            "            \"overview\": \"An apocalyptic story set in the furthest reaches of our planet, in a stark desert landscape where humanity is broken, and most everyone is crazed fighting for the necessities of life. Within this world exist two rebels on the run who just might be able to restore order. There's Max, a man of action and a man of few words, who seeks peace of mind following the loss of his wife and child in the aftermath of the chaos. And Furiosa, a woman of action and a woman who believes her path to survival may be achieved if she can make it across the desert back to her childhood homeland.\",\n" +
            "            \"release_date\": \"2015-05-13\",\n" +
            "            \"genre_ids\": [\n" +
            "                28,\n" +
            "                12,\n" +
            "                878,\n" +
            "                53\n" +
            "            ],\n" +
            "            \"id\": 76341,\n" +
            "            \"original_title\": \"Mad Max: Fury Road\",\n" +
            "            \"original_language\": \"en\",\n" +
            "            \"title\": \"Mad Max: Fury Road\",\n" +
            "            \"backdrop_path\": \"/phszHPFVhPHhMZgo0fWTKBDQsJA.jpg\",\n" +
            "            \"popularity\": 33.02187,\n" +
            "            \"vote_count\": 7224,\n" +
            "            \"video\": false,\n" +
            "            \"vote_average\": 7.2\n" +
            "        },\n" +
            "        {\n" +
            "            \"poster_path\": \"/aybgjbFbn6yUbsgUMnUbwc2jcWd.jpg\",\n" +
            "            \"adult\": false,\n" +
            "            \"overview\": \"When a wounded Christian Grey tries to entice a cautious Ana Steele back into his life, she demands a new arrangement before she will give him another chance. As the two begin to build trust and find stability, shadowy figures from Christian’s past start to circle the couple, determined to destroy their hopes for a future together.\",\n" +
            "            \"release_date\": \"2017-02-08\",\n" +
            "            \"genre_ids\": [\n" +
            "                18,\n" +
            "                10749\n" +
            "            ],\n" +
            "            \"id\": 341174,\n" +
            "            \"original_title\": \"Fifty Shades Darker\",\n" +
            "            \"original_language\": \"en\",\n" +
            "            \"title\": \"Fifty Shades Darker\",\n" +
            "            \"backdrop_path\": \"/rXBB8F6XpHAwci2dihBCcixIHrK.jpg\",\n" +
            "            \"popularity\": 32.53495,\n" +
            "            \"vote_count\": 1224,\n" +
            "            \"video\": false,\n" +
            "            \"vote_average\": 5.9\n" +
            "        },\n" +
            "        {\n" +
            "            \"poster_path\": \"/4PiiNGXj1KENTmCBHeN6Mskj2Fq.jpg\",\n" +
            "            \"adult\": false,\n" +
            "            \"overview\": \"After his career is destroyed, a brilliant but arrogant surgeon gets a new lease on life when a sorcerer takes him under his wing and trains him to defend the world against evil.\",\n" +
            "            \"release_date\": \"2016-10-25\",\n" +
            "            \"genre_ids\": [\n" +
            "                28,\n" +
            "                12,\n" +
            "                14,\n" +
            "                878\n" +
            "            ],\n" +
            "            \"id\": 284052,\n" +
            "            \"original_title\": \"Doctor Strange\",\n" +
            "            \"original_language\": \"en\",\n" +
            "            \"title\": \"Doctor Strange\",\n" +
            "            \"backdrop_path\": \"/tFI8VLMgSTTU38i8TIsklfqS9Nl.jpg\",\n" +
            "            \"popularity\": 30.76624,\n" +
            "            \"vote_count\": 3040,\n" +
            "            \"video\": false,\n" +
            "            \"vote_average\": 6.9\n" +
            "        },\n" +
            "        {\n" +
            "            \"poster_path\": \"/5vHssUeVe25bMrof1HyaPyWgaP.jpg\",\n" +
            "            \"adult\": false,\n" +
            "            \"overview\": \"Ex-hitman John Wick comes out of retirement to track down the gangsters that took everything from him.\",\n" +
            "            \"release_date\": \"2014-10-22\",\n" +
            "            \"genre_ids\": [\n" +
            "                28,\n" +
            "                53\n" +
            "            ],\n" +
            "            \"id\": 245891,\n" +
            "            \"original_title\": \"John Wick\",\n" +
            "            \"original_language\": \"en\",\n" +
            "            \"title\": \"John Wick\",\n" +
            "            \"backdrop_path\": \"/mFb0ygcue4ITixDkdr7wm1Tdarx.jpg\",\n" +
            "            \"popularity\": 28.853298,\n" +
            "            \"vote_count\": 3569,\n" +
            "            \"video\": false,\n" +
            "            \"vote_average\": 7\n" +
            "        },\n" +
            "        {\n" +
            "            \"poster_path\": \"/y31QB9kn3XSudA15tV7UWQ9XLuW.jpg\",\n" +
            "            \"adult\": false,\n" +
            "            \"overview\": \"Light years from Earth, 26 years after being abducted, Peter Quill finds himself the prime target of a manhunt after discovering an orb wanted by Ronan the Accuser.\",\n" +
            "            \"release_date\": \"2014-07-30\",\n" +
            "            \"genre_ids\": [\n" +
            "                28,\n" +
            "                878,\n" +
            "                12\n" +
            "            ],\n" +
            "            \"id\": 118340,\n" +
            "            \"original_title\": \"Guardians of the Galaxy\",\n" +
            "            \"original_language\": \"en\",\n" +
            "            \"title\": \"Guardians of the Galaxy\",\n" +
            "            \"backdrop_path\": \"/bHarw8xrmQeqf3t8HpuMY7zoK4x.jpg\",\n" +
            "            \"popularity\": 27.255929,\n" +
            "            \"vote_count\": 6639,\n" +
            "            \"video\": false,\n" +
            "            \"vote_average\": 7.9\n" +
            "        },\n" +
            "        {\n" +
            "            \"poster_path\": \"/kSBXou5Ac7vEqKd97wotJumyJvU.jpg\",\n" +
            "            \"adult\": false,\n" +
            "            \"overview\": \"Following the events of Age of Ultron, the collective governments of the world pass an act designed to regulate all superhuman activity. This polarizes opinion amongst the Avengers, causing two factions to side with Iron Man or Captain America, which causes an epic battle between former allies.\",\n" +
            "            \"release_date\": \"2016-04-27\",\n" +
            "            \"genre_ids\": [\n" +
            "                28,\n" +
            "                878\n" +
            "            ],\n" +
            "            \"id\": 271110,\n" +
            "            \"original_title\": \"Captain America: Civil War\",\n" +
            "            \"original_language\": \"en\",\n" +
            "            \"title\": \"Captain America: Civil War\",\n" +
            "            \"backdrop_path\": \"/m5O3SZvQ6EgD5XXXLPIP1wLppeW.jpg\",\n" +
            "            \"popularity\": 26.310569,\n" +
            "            \"vote_count\": 4957,\n" +
            "            \"video\": false,\n" +
            "            \"vote_average\": 6.9\n" +
            "        },\n" +
            "        {\n" +
            "            \"poster_path\": \"/inVq3FRqcYIRl2la8iZikYYxFNR.jpg\",\n" +
            "            \"adult\": false,\n" +
            "            \"overview\": \"Based upon Marvel Comics’ most unconventional anti-hero, DEADPOOL tells the origin story of former Special Forces operative turned mercenary Wade Wilson, who after being subjected to a rogue experiment that leaves him with accelerated healing powers, adopts the alter ego Deadpool. Armed with his new abilities and a dark, twisted sense of humor, Deadpool hunts down the man who nearly destroyed his life.\",\n" +
            "            \"release_date\": \"2016-02-09\",\n" +
            "            \"genre_ids\": [\n" +
            "                28,\n" +
            "                12,\n" +
            "                35,\n" +
            "                10749\n" +
            "            ],\n" +
            "            \"id\": 293660,\n" +
            "            \"original_title\": \"Deadpool\",\n" +
            "            \"original_language\": \"en\",\n" +
            "            \"title\": \"Deadpool\",\n" +
            "            \"backdrop_path\": \"/n1y094tVDFATSzkTnFxoGZ1qNsG.jpg\",\n" +
            "            \"popularity\": 25.146624,\n" +
            "            \"vote_count\": 7404,\n" +
            "            \"video\": false,\n" +
            "            \"vote_average\": 7.3\n" +
            "        },\n" +
            "        {\n" +
            "            \"poster_path\": \"/bbxtz5V0vvnTDA2qWbiiRC77Ok9.jpg\",\n" +
            "            \"adult\": false,\n" +
            "            \"overview\": \"Julia becomes worried about her boyfriend, Holt when he explores the dark urban legend of a mysterious videotape said to kill the watcher seven days after viewing. She sacrifices herself to save her boyfriend and in doing so makes a horrifying discovery: there is a \\\"movie within the movie\\\" that no one has ever seen before.\",\n" +
            "            \"release_date\": \"2017-02-01\",\n" +
            "            \"genre_ids\": [\n" +
            "                27\n" +
            "            ],\n" +
            "            \"id\": 14564,\n" +
            "            \"original_title\": \"Rings\",\n" +
            "            \"original_language\": \"en\",\n" +
            "            \"title\": \"Rings\",\n" +
            "            \"backdrop_path\": \"/biN2sqExViEh8IYSJrXlNKjpjxx.jpg\",\n" +
            "            \"popularity\": 24.847406,\n" +
            "            \"vote_count\": 481,\n" +
            "            \"video\": false,\n" +
            "            \"vote_average\": 4.7\n" +
            "        },\n" +
            "        {\n" +
            "            \"poster_path\": \"/h2mhfbEBGABSHo2vXG1ECMKAJa7.jpg\",\n" +
            "            \"adult\": false,\n" +
            "            \"overview\": \"The six-member crew of the International Space Station is tasked with studying a sample from Mars that may be the first proof of extra-terrestrial life, which proves more intelligent than ever expected.\",\n" +
            "            \"release_date\": \"2017-03-23\",\n" +
            "            \"genre_ids\": [\n" +
            "                27,\n" +
            "                878,\n" +
            "                53\n" +
            "            ],\n" +
            "            \"id\": 395992,\n" +
            "            \"original_title\": \"Life\",\n" +
            "            \"original_language\": \"en\",\n" +
            "            \"title\": \"Life\",\n" +
            "            \"backdrop_path\": \"/3pTI3ExOIdhqfNDAPpaK0wbbvAK.jpg\",\n" +
            "            \"popularity\": 23.204308,\n" +
            "            \"vote_count\": 103,\n" +
            "            \"video\": false,\n" +
            "            \"vote_average\": 6.3\n" +
            "        }\n" +
            "    ],\n" +
            "    \"total_results\": 19627,\n" +
            "    \"total_pages\": 982\n" +
            "}";
}
