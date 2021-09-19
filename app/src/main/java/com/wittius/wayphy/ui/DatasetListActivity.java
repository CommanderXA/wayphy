/* Copyright 2019 The TensorFlow Authors. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
==============================================================================*/
package com.wittius.wayphy.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wittius.wayphy.R;
import com.wittius.wayphy.ml.LoadDatasetClient;

/**
 * An activity representing a list of Datasets. This activity has different presentations for
 * handset and tablet-size devices. On handsets, the activity presents a list of items, which when
 * touched, lead to a {@link QaActivity} representing item details. On tablets, the activity
 * presents the list of items and item details side-by-side using two vertical panes.
 */
public class DatasetListActivity extends AppCompatActivity {

  private Toolbar mTopToolbar;

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if(id == R.id.menu_about){
      Intent i = new Intent(this, AboutActivity.class);
      startActivity(i);
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dataset_list);

    ListView listView = findViewById(R.id.dataset_list);
    assert listView != null;

    LoadDatasetClient datasetClient = new LoadDatasetClient(this);
    ArrayAdapter<String> datasetAdapter =
        new ArrayAdapter<>(
            this, R.layout.list_text_changer, datasetClient.getTitles());
    listView.setAdapter(datasetAdapter);

    listView.setOnItemClickListener(
        (parent, view, position, id) -> {
          startActivity(QaActivity.newInstance(this, position));
        });

    mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
    setSupportActionBar(mTopToolbar);
    getSupportActionBar().setTitle("People");
    mTopToolbar.setTitleTextAppearance(this, R.style.NunitoTextAppearance);
    getSupportActionBar().setElevation(0);

    FloatingActionButton fab = findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://commanderxa.github.io/wayphy/"));
        startActivity(browserIntent);
      }
    });
  }
}
