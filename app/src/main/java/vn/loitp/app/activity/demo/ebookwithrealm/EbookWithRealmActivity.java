package vn.loitp.app.activity.demo.ebookwithrealm;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import loitp.basemaster.R;
import vn.loitp.app.activity.demo.ebookwithrealm.adapters.BooksAdapter;
import vn.loitp.app.activity.demo.ebookwithrealm.adapters.RealmBooksAdapter;
import vn.loitp.app.activity.demo.ebookwithrealm.model.Book;
import vn.loitp.app.activity.demo.ebookwithrealm.realm.RealmController;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LPref;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.utils.util.ToastUtils;

//https://www.androidhive.info/2016/05/android-working-with-realm-database-replacing-sqlite-core-data/
public class EbookWithRealmActivity extends BaseActivity {
    private BooksAdapter booksAdapter;
    private Realm realm;
    private LayoutInflater layoutInflater;
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        //get realm instance
        this.realm = RealmController.with(this).getRealm();

        setupRecycler();

        if (!LPref.getPreLoad(activity)) {
            setRealmData();
        }

        // refresh the realm instance
        RealmController.with(this).refresh();
        // get all persisted objects
        // create the helper booksAdapter and notify data set changes
        // changes will be reflected automatically
        setRealmAdapter(RealmController.with(this).getBooks());

        ToastUtils.showShort("Press card item for edit, long press to remove item");

        //add new item
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_ebook_with_realm;
    }

    private void setRealmAdapter(RealmResults<Book> books) {
        RealmBooksAdapter realmBooksAdapter = new RealmBooksAdapter(this.getApplicationContext(), books, true);
        // Set the data and tell the RecyclerView to draw
        booksAdapter.setRealmAdapter(realmBooksAdapter);
        booksAdapter.notifyDataSetChanged();
    }

    private void setupRecycler() {
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        booksAdapter = new BooksAdapter(this, new BooksAdapter.OnClick() {
            @Override
            public void onClick(Book book, int position) {
                updateItem(book, position);
            }

            @Override
            public void onLongClick(int position) {
                deleteItem(position);
            }
        });
        recyclerView.setAdapter(booksAdapter);
        LUIUtil.setPullLikeIOSVertical(recyclerView);
    }

    private void setRealmData() {
        ArrayList<Book> books = new ArrayList<>();

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

        for (Book b : books) {
            // Persist your data easily
            realm.beginTransaction();
            realm.copyToRealm(b);
            realm.commitTransaction();
        }

        LPref.setPreLoad(activity, true);
    }

    private void addItem() {
        layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.real_edit_item, null);
        final EditText editTitle = (EditText) view.findViewById(R.id.title);
        final EditText editAuthor = (EditText) view.findViewById(R.id.author);
        final EditText editThumbnail = (EditText) view.findViewById(R.id.thumbnail);

        editThumbnail.setText("https://kenh14cdn.com/2016/photo-4-1470640589710.jpg");

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view)
                .setTitle("Add book")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Book book = new Book();
                        book.setId(RealmController.getInstance().getBooks().size() + System.currentTimeMillis());
                        book.setTitle(editTitle.getText().toString());
                        book.setAuthor(editAuthor.getText().toString());
                        book.setImageUrl(editThumbnail.getText().toString());

                        if (editTitle.getText() == null || editTitle.getText().toString().equals("") || editTitle.getText().toString().equals(" ")) {
                            ToastUtils.showShort("Entry not saved, missing title");
                        } else {
                            // Persist your data easily
                            realm.beginTransaction();
                            realm.copyToRealm(book);
                            realm.commitTransaction();

                            booksAdapter.notifyDataSetChanged();
                            recyclerView.smoothScrollToPosition(RealmController.getInstance().getBooks().size() - 1);

                            /*booksAdapter.notifyItemInserted(RealmController.getInstance().getBooks().size() - 1);
                            booksAdapter.notifyItemRangeChanged(RealmController.getInstance().getBooks().size() - 1, RealmController.getInstance().getBooks().size());
                            recyclerView.scrollToPosition(RealmController.getInstance().getBooks().size() - 1);*/
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void updateItem(Book book, int position) {
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View content = layoutInflater.inflate(R.layout.real_edit_item, null);
        final EditText editTitle = (EditText) content.findViewById(R.id.title);
        final EditText editAuthor = (EditText) content.findViewById(R.id.author);
        final EditText editThumbnail = (EditText) content.findViewById(R.id.thumbnail);

        editTitle.setText(book.getTitle());
        editAuthor.setText(book.getAuthor());
        editThumbnail.setText(book.getImageUrl());

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(content)
                .setTitle("Edit Book")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RealmResults<Book> results = realm.where(Book.class).findAll();

                        realm.beginTransaction();
                        results.get(position).setAuthor(editAuthor.getText().toString());
                        results.get(position).setTitle(editTitle.getText().toString());
                        results.get(position).setImageUrl(editThumbnail.getText().toString());

                        realm.commitTransaction();

                        booksAdapter.notifyItemChanged(position);
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteItem(int position) {
        RealmResults<Book> results = realm.where(Book.class).findAll();

        // Get the book title to show it in toast message
        Book book = results.get(position);
        String title = book.getTitle();

        // All changes to data must happen in a transaction
        realm.beginTransaction();

        // remove single match
        results.remove(position);
        realm.commitTransaction();

        if (results.size() == 0) {
            LPref.setPreLoad(activity, false);
        }

        booksAdapter.notifyItemRemoved(position);
        booksAdapter.notifyItemRangeChanged(position, RealmController.getInstance().getBooks().size());

        ToastUtils.showShort("Removed book: " + title);

        if (RealmController.getInstance().getBooks().isEmpty()) {
            ToastUtils.showShort("getBooks().isEmpty()");
        }
    }
}
