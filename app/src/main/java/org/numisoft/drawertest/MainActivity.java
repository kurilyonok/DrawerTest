package org.numisoft.drawertest;

import android.app.SearchManager;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupWindow;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveClient;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.numisoft.drawertest.booksrecycler.BooksAdapter;
import org.numisoft.drawertest.booksrecycler.OnBookClickListener;
import org.numisoft.drawertest.booksrecycler.SmoothLayoutManager;
import org.numisoft.drawertest.calendarrecycler.OnYearClickListener;
import org.numisoft.drawertest.model.NavMenuItem;
import org.numisoft.drawertest.model.HeaderItem;
import org.numisoft.drawertest.model.AuthorItem;
import org.numisoft.drawertest.model.TitleItem;
import org.numisoft.drawertest.model.Year;
import org.numisoft.drawertest.recycler.NavMenuAdapter;
import org.numisoft.drawertest.recycler.OnNavMenuClickListener;
import org.numisoft.drawertest.room.AppDatabase;
import org.numisoft.drawertest.room.entities.Author;
import org.numisoft.drawertest.room.entities.Book;
import org.numisoft.drawertest.room.entities.BookWithAuthor;
import org.numisoft.drawertest.viewmodel.BooksViewModel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements OnBookClickListener, OnYearClickListener, OnNavMenuClickListener {

    public static final int REQUEST_CODE_SIGN_IN = 123;
    public static final String TAG = "DrawerTest >>>";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.recycler_books)
    RecyclerView rvBooks;

    @BindView(R.id.recycler_menu)
    RecyclerView rvMenu;

    @BindView(R.id.appbar)
    AppBarLayout appbar;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsing;


    private SearchView searchView;
    private BooksAdapter adapter;
    private AppDatabase database;
    private BooksViewModel vmBooks;
    private PopupWindow window;
    private List<BookWithAuthor> allBooks;
    private LiveData<List<BookWithAuthor>> liveData;

    private GoogleSignInClient googleSignInClient;
    private DriveClient driveClient;
    private DriveResourceClient driveResourceClient;
    private List<NavMenuItem> navMenuItems;
    private NavMenuAdapter navMenuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupToolbar();
        setupDrawer();
        setupNavigationRecycler();
        setupBookRecycler();

        database = AppDatabase.getInstance(getApplicationContext());
        addItems();

        allBooks = new ArrayList<>();
        vmBooks = ViewModelProviders.of(this).get(BooksViewModel.class);
        vmBooks.getBooks().observe(MainActivity.this,
                newBooks -> {
                    allBooks.clear();
                    allBooks.addAll(newBooks);
                    updateNavMenu();
                });

        GradientDrawable gradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.BL_TR,
                new int[]{
                        ContextCompat.getColor(this, R.color.color1),
                        ContextCompat.getColor(this, R.color.color2)});

        appbar.setBackground(gradientDrawable);
//        appbar.setBackground(getDrawable(R.drawable.gradient));


//        GOOGLE DRIVE STUFF
//        googleSignInClient = buildGoogleSignInClient();
//        startActivityForResult(googleSignInClient.getSignInIntent(), REQUEST_CODE_SIGN_IN);

    }


    private void addItems() {
        AsyncTask.execute(() -> {
            List<Book> books = database.bookDao().getAll();
            List<Author> authors = database.authorDao().getAuthors();
            if (books.isEmpty() || authors.isEmpty()) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                String authorsJson = loadJSONFromAsset(R.raw.authors);
                Author[] authorsArray = gson.fromJson(authorsJson, Author[].class);
                authors = Arrays.asList(authorsArray);
                for (Author a : authors) database.authorDao().insert(a);

                String booksJson = loadJSONFromAsset(R.raw.books);
                Book[] booksArray = gson.fromJson(booksJson, Book[].class);
                books = Arrays.asList(booksArray);
                for (Book b : books) database.bookDao().insert(b);

            }
        });
    }

    public String loadJSONFromAsset(int resourcesId) {
        String json;
        try {
            InputStream is = getResources().openRawResource(resourcesId);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void setupBookRecycler() {
        adapter = new BooksAdapter(this);
        rvBooks.setAdapter(adapter);

        LinearLayoutManager layoutManager = new SmoothLayoutManager(this, 4);
        rvBooks.setLayoutManager(layoutManager);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Book book = (Book) viewHolder.itemView.getTag();

                int itemCount = rvBooks.getAdapter().getItemCount();
                int scrollTo = -1;

                if (viewHolder.getAdapterPosition() == 0) {
                    scrollTo = 0;
                } else if (viewHolder.getAdapterPosition() == itemCount - 1) {
                    scrollTo = itemCount - 1;
                }
                deleteBook(book, scrollTo);
            }
        }).attachToRecyclerView(rvBooks);
    }

    private void setupNavigationRecycler() {
        navMenuItems = new ArrayList<NavMenuItem>() {{
            add(new HeaderItem());
            add(new AuthorItem("Dostoyevsky"));
            add(new AuthorItem("Tolstoi"));
            add(new AuthorItem("Pushkin"));
            add(new AuthorItem("Lermontov"));
            add(new TitleItem("Title 1"));
            add(new AuthorItem("Item 5"));
            add(new AuthorItem("Item 6"));
            add(new AuthorItem("Item 7"));
            add(new AuthorItem("Item 8"));
            add(new TitleItem("Title 2"));
            add(new AuthorItem("Item 9"));
            add(new AuthorItem("Item 10"));
            add(new AuthorItem("Item 11"));
            add(new AuthorItem("Item 12"));
            add(new TitleItem("Title 3"));
            add(new AuthorItem("Item 13"));
            add(new AuthorItem("Item 14"));
            add(new AuthorItem("Item 15"));
        }};

        navMenuAdapter = new NavMenuAdapter(navMenuItems, this);
        rvMenu.setLayoutManager(new LinearLayoutManager(this));
        rvMenu.setAdapter(navMenuAdapter);
    }

    private void updateNavMenu() {

        Map<String, Pair<Integer, Integer>> authorsMap = new HashMap<>();

        for (BookWithAuthor b : allBooks) {

            String lastName = b.getAuthors().get(0).getLastName();

            if (authorsMap.containsKey(lastName)) {
                Pair<Integer, Integer> pair = authorsMap.get(lastName);

                int bookCount = pair.first;
                bookCount++;

                int favoriteCount = pair.second;
                if (b.getBook().isFavorite()) favoriteCount++;

                Pair<Integer, Integer> newPair = new Pair<>(bookCount, favoriteCount);

                authorsMap.put(lastName, newPair);

            } else {
                authorsMap.put(lastName, new Pair<>(0, 0));
            }
        }

//        for (NavMenuItem navItem : navMenuItems) {
//
//            if (navItem instanceof AuthorItem) {
//                String lastName = ((AuthorItem) navItem).getLastName();
//                ((AuthorItem) navItem).setBooksCount(authorsMap.get(lastName).first);
//                ((AuthorItem) navItem).setFavoriteCount(authorsMap.get(lastName).second);
//            }
//
//        }




    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
    }

    private void setupDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_items, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        adapter.getFilter().filter(query);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String query) {
                        adapter.getFilter().filter(query);
                        return false;
                    }
                });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.calendar) {
            if (window != null && window.isShowing()) {
                window.dismiss();
            } else {
                showPopupWindow();
            }
        }

        if (item.getItemId() == R.id.settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        return true;
    }

    private void showPopupWindow() {
        window = new CalendarWindow(toolbar, this, allBooks);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (!searchView.isIconified()) {
            searchView.setIconified(true);
        } else if (window != null && window.isShowing()) {
            window.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onFavoriteClick(Book book) {
        vmBooks.toggleFavorite(book);
    }

    @Override
    public void onBookClick(Book book) {
        collapsing.setTitle(book.getTitle());
    }

    private void deleteBook(Book book, int scrollTo) {
        vmBooks.deleteBook(book);
        undo(book, scrollTo);
    }

    private void undo(Book book, int scrollTo) {
        Snackbar.make(toolbar, book.getTitle().concat(" was deleted"), Snackbar.LENGTH_LONG)
                .setAction("Undo", view -> {
                    insertBook(book);
                    if (scrollTo != -1) smoothScrollToPosition(scrollTo, true);
                })
                .setActionTextColor(getResources().getColor(R.color.colorAccent))
                .show();
    }

    private void smoothScrollToPosition(int position, boolean smooth) {
        new Handler().postDelayed(() -> {
            if (smooth) {
                rvBooks.smoothScrollToPosition(position);
            } else {
                rvBooks.scrollToPosition(position);
            }
        }, 500);
    }

    private void insertBook(Book book) {
        vmBooks.insertBook(book);
    }

    private GoogleSignInClient buildGoogleSignInClient() {
        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestScopes(Drive.SCOPE_APPFOLDER)
                        .build();

        GoogleSignInClient client = GoogleSignIn.getClient(this, signInOptions);
        return client;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SIGN_IN:
                Log.wtf(TAG, "Sign in request code");
                if (resultCode == RESULT_OK) {
                    Log.wtf(TAG, "Signed in successfully.");
                    driveClient = Drive.getDriveClient(this, GoogleSignIn.getLastSignedInAccount(this));
                    driveResourceClient = Drive.getDriveResourceClient(this, GoogleSignIn.getLastSignedInAccount(this));
                    createFileInAppFolder();

                    driveResourceClient.getAppFolder()
                            .continueWithTask(folderTask -> driveResourceClient.listChildren(folderTask.getResult()))
                            .addOnSuccessListener(this,
                                    metadataBuffer -> {
                                        for (Metadata m : metadataBuffer) {
                                            Log.wtf("listChildren", m.getOriginalFilename());
                                        }
                                        metadataBuffer.release();
                                    });
                }
                break;
        }
    }

    private void createFileInAppFolder() {
        Task<DriveFolder> appFolderTask = driveResourceClient.getAppFolder();
        Task<DriveContents> createContentsTask = driveResourceClient.createContents();
        Tasks.whenAll(appFolderTask, createContentsTask)
                .continueWithTask(task -> {
                    DriveFolder parent = appFolderTask.getResult();
                    DriveContents contents = createContentsTask.getResult();
                    OutputStream outputStream = contents.getOutputStream();
                    try (Writer writer = new OutputStreamWriter(outputStream)) {
                        writer.write("Hello World!");
                    }

                    MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                            .setTitle("New file 3")
                            .setMimeType("text/plain")
                            .setStarred(true)
                            .build();

                    return driveResourceClient.createFile(parent, changeSet, contents);
                })
                .addOnSuccessListener(this, driveFile -> Log.wtf("createFileInAppFolder", driveFile.getDriveId().encodeToString()))
                .addOnFailureListener(this, e -> Log.wtf(TAG, "Unable to create file", e));
    }

    @Override
    public void onYearClick(Year year) {
        window.dismiss();
        collapsing.setTitle(year.getTitle());

        if (liveData != null && liveData.hasObservers()) {
            liveData.removeObservers(this);
        }

        if (year.getType() == Year.TYPE_HEADER) {
            liveData = vmBooks.getBooks();
        } else {
            liveData = vmBooks.getBooksByYear(year.getTitle());
        }

        liveData.observe(MainActivity.this, bookWithAuthors -> adapter.updateItems(bookWithAuthors));
        smoothScrollToPosition(0, true);
    }

    @Override
    public void onAuthorClick(int position) {
        drawerLayout.closeDrawer(GravityCompat.START);
        collapsing.setTitle(((AuthorItem) navMenuItems.get(position)).getLastName());

        if (liveData != null && liveData.hasObservers()) {
            liveData.removeObservers(this);
        }

        liveData = vmBooks.getBooksByAuthor(position);
        liveData.observe(MainActivity.this, bookWithAuthors -> adapter.updateItems(bookWithAuthors));
        smoothScrollToPosition(0, true);
    }

}
