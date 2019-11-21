package org.Equipo2.pokedex.actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.Equipo2.pokedex.R;
import org.Equipo2.pokedex.adaptadores.PokemonAdapter;
import org.Equipo2.pokedex.database.AppDatabase;
import org.Equipo2.pokedex.entities.Pokemon;

import java.util.List;

public class FavoritesActivity extends AppCompatActivity implements PokemonAdapter.ItemClickListener {

    PokemonAdapter adapter;
    RecyclerView recyclerView;
    AppDatabase database;

    List<Pokemon> favoritePokemons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_pokemon);

        database = AppDatabase.getDatabase(this);
        favoritePokemons = database.pokemonDao().getAll();

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new PokemonAdapter(this, favoritePokemons);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        favoritePokemons.clear();
        favoritePokemons.addAll(database.pokemonDao().getAll());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {
        Pokemon selectedPokemon = adapter.getPokemon(position);

        Intent intent = new Intent(this, PokemonDetailsActivity.class);
        intent.putExtra("URL", selectedPokemon.getUrl());
        startActivity(intent);
    }
}
