package vn.loitp.up.a.demo.ebookWithRealm;

import static com.loitp.core.ext.PrefKt.getPreLoad;
import static com.loitp.core.ext.PrefKt.setPreLoad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.loitp.annotation.IsFullScreen;
import com.loitp.annotation.LogTag;
import com.loitp.core.base.BaseActivityFont;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmResults;
import vn.loitp.R;
import vn.loitp.up.a.demo.ebookWithRealm.adt.BooksAdapter;
import vn.loitp.up.a.demo.ebookWithRealm.adt.RealmBooksAdapter;
import vn.loitp.up.a.demo.ebookWithRealm.md.Book;
import vn.loitp.up.a.demo.ebookWithRealm.realm.RealmController;

//https://www.androidhive.info/2016/05/android-working-with-realm-database-replacing-sqlite-core-data/

//8.1.2021 tried to retry kotlin
@LogTag("EbookWithRealmActivity")
@IsFullScreen(false)
public class EbookWithRealmActivity extends BaseActivityFont {
    private BooksAdapter booksAdapter;
    private Realm realm;
    private LayoutInflater layoutInflater;
    private RecyclerView recyclerView;

    //    @Override
    //    protected int setLayoutResourceId() {
    //        return R.layout.a_ebook_with_realm;
    //    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_ebook_with_realm);
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recycler);

        //get realm instance
        this.realm = Objects.requireNonNull(RealmController.Companion.with(this)).getRealm();

        setupRecycler();

        if (!getPreLoad(this)) {
            setRealmData();
        }

        // refresh the realm instance
        Objects.requireNonNull(RealmController.Companion.with(this)).refresh();
        // get all persisted objects
        // create the helper booksAdapter and notify data set changes
        // changes will be reflected automatically
        setRealmAdapter(Objects.requireNonNull(RealmController.Companion.with(this)).getBooks());

        showShortInformation(
                "Press card item for edit, long press to remove item",
                true,
                null,
                null,
                null,
                null,
                32
        );

        //add new item
        floatingActionButton.setOnClickListener(v -> addItem());
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setRealmAdapter(RealmResults<Book> books) {
        final RealmBooksAdapter realmBooksAdapter = new RealmBooksAdapter(this.getApplicationContext(), books, true);
        // Set the data and tell the RecyclerView to draw
        booksAdapter.setRealmAdapter(realmBooksAdapter);
        booksAdapter.notifyDataSetChanged();
    }

    private void setupRecycler() {
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        booksAdapter = new BooksAdapter(this, new BooksAdapter.OnClick() {
            @Override
            public void onClick(@NotNull Book book, int position) {
                updateItem(book, position);
            }

            @Override
            public void onLongClick(int position) {
                deleteItem(position);
            }
        });
        recyclerView.setAdapter(booksAdapter);
    }

    private void setRealmData() {
        final ArrayList<Book> books = new ArrayList<>();

        Book book = new Book();
        book.setId(1 + System.currentTimeMillis());
        book.setAuthor("1 Reto Meier");
        book.setTitle("1 Android 4 Application Development");
        book.setImageUrl("http://api.androidhive.info/images/realm/1.png");
        books.add(book);

        book = new Book();
        book.setId(2 + System.currentTimeMillis());
        book.setAuthor("2 Itzik Ben-Gan");
        book.setTitle("2 Microsoft SQL Server 2012 T-SQL Fundamentals");
        book.setImageUrl("http://api.androidhive.info/images/realm/2.png");
        books.add(book);

        book = new Book();
        book.setId(3 + System.currentTimeMillis());
        book.setAuthor("3 Magnus Lie Hetland");
        book.setTitle("3 Beginning Python: From Novice To Professional Paperback");
        book.setImageUrl("http://api.androidhive.info/images/realm/3.png");
        books.add(book);

        book = new Book();
        book.setId(4 + System.currentTimeMillis());
        book.setAuthor("4 Chad Fowler");
        book.setTitle("4 The Passionate Programmer: Creating a Remarkable Career in Software Development");
        book.setImageUrl("http://api.androidhive.info/images/realm/4.png");
        books.add(book);

        book = new Book();
        book.setId(5 + System.currentTimeMillis());
        book.setAuthor("5 Yashavant Kanetkar");
        book.setTitle("5 Written Test Questions In C Programming");
        book.setImageUrl("http://api.androidhive.info/images/realm/5.png");
        books.add(book);

        for (final Book b : books) {
            // Persist your data easily
            realm.beginTransaction();
            realm.copyToRealm(b);
            realm.commitTransaction();
        }

        setPreLoad(this, true);
    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    private void addItem() {
        layoutInflater = getLayoutInflater();
        @SuppressLint("InflateParams") final View view = layoutInflater.inflate(R.layout.real_edit_item, null);
        final EditText editTitle = view.findViewById(R.id.title);
        final EditText editAuthor = view.findViewById(R.id.author);
        final EditText editThumbnail = view.findViewById(R.id.thumbnail);

        editThumbnail.setText("https://kenh14cdn.com/2016/photo-4-1470640589710.jpg");

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view)
                .setTitle("Add book")
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    final Book book = new Book();
                    book.setId(Objects.requireNonNull(RealmController.getInstance()).getBooks().size() + System.currentTimeMillis());
                    book.setTitle(editTitle.getText().toString());
                    book.setAuthor(editAuthor.getText().toString());
                    book.setImageUrl(editThumbnail.getText().toString());

                    if (editTitle.getText() == null || editTitle.getText().toString().equals("") || editTitle.getText().toString().equals(" ")) {
                        showShortInformation("Entry not saved, missing title",
                                true,
                                null,
                                null,
                                null,
                                null,
                                32
                        );
                    } else {
                        // Persist your data easily
                        realm.beginTransaction();
                        realm.copyToRealm(book);
                        realm.commitTransaction();

                        booksAdapter.notifyDataSetChanged();
                        recyclerView.smoothScrollToPosition(RealmController.getInstance().getBooks().size() - 1);

                    }
                })
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.dismiss());
        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    @SuppressLint("InflateParams")
    private void updateItem(final Book book, final int position) {
        layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View content = null;
        if (layoutInflater != null) {
            content = layoutInflater.inflate(R.layout.real_edit_item, null);
        }
        if (content == null) {
            return;
        }
        final EditText editTitle = content.findViewById(R.id.title);
        final EditText editAuthor = content.findViewById(R.id.author);
        final EditText editThumbnail = content.findViewById(R.id.thumbnail);

        editTitle.setText(book.getTitle());
        editAuthor.setText(book.getAuthor());
        editThumbnail.setText(book.getImageUrl());

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(content)
                .setTitle("Edit Book")
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    final RealmResults<Book> results = realm.where(Book.class).findAll();

                    realm.beginTransaction();
                    results.get(position).setAuthor(editAuthor.getText().toString());
                    results.get(position).setTitle(editTitle.getText().toString());
                    results.get(position).setImageUrl(editThumbnail.getText().toString());

                    realm.commitTransaction();

                    booksAdapter.notifyItemChanged(position);
                })
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteItem(int position) {
        final RealmResults<Book> results = realm.where(Book.class).findAll();

        // Get the book title to show it in toast message
        final Book book = results.get(position);
        final String title = book.getTitle();

        // All changes to data must happen in a transaction
        realm.beginTransaction();

        // remove single match
        results.remove(position);
        realm.commitTransaction();

        if (results.size() == 0) {
            setPreLoad(this, false);
        }

        booksAdapter.notifyItemRemoved(position);
        booksAdapter.notifyItemRangeChanged(position, Objects.requireNonNull(RealmController.getInstance()).getBooks().size());

        showShortInformation("Removed book: " + title,
                true,
                null,
                null,
                null,
                null,
                32
        );

        if (RealmController.getInstance().getBooks().isEmpty()) {
            showShortInformation(
                    "getMyBookList().isEmpty()",
                    true,
                    null,
                    null,
                    null,
                    null,
                    32
            );
        }
    }
}
