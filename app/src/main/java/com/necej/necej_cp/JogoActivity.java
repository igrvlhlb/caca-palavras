package com.necej.necej_cp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.necej.necej_cp.jogo_utils.DicasViewAdapter;
import com.necej.necej_cp.jogo_utils.Dificuldades;
import com.necej.necej_cp.jogo_utils.Grade;
import com.necej.necej_cp.jogo_utils.ResourcesAdditions;
import com.necej.necej_cp.jogo_utils.Tabuleiro;
import com.necej.necej_cp.jogo_utils.TabuleiroView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;
public class JogoActivity extends AppCompatActivity {

    int mImageSize;
    private TabuleiroView mTabuleiroView;
    private Tabuleiro mTabuleiro;
    private Grade mGrade;
    private RecyclerView recyclerDicas;
    private RecyclerView.Adapter mDicasViewAdapter;
    private RecyclerView.LayoutManager mLmanager;
    private String mDificuldade;
    private static Bundle descricoes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogo);
        mDificuldade = getIntent().getExtras().getString(TelaInicialActivity.EXTRA_DIFICULDADE);
        Log.i(getClass().getSimpleName(),mDificuldade);
        //this.hideBar();
        mTabuleiroView = findViewById(R.id.tabuleiro_image);
        mGrade = new Grade(12,12);
        descricoes = ResourcesAdditions.getResourcesExtras(getResources(),"palavras-desc",R.xml.descricoes);
        gradeInit();
        this.criaTabuleiro();
    }

    private void hideBar(){
        try {
            ActionBar tmpAct = getSupportActionBar();
            if (tmpAct != null) tmpAct.hide();
        } catch (Exception e) {
            Logger.getAnonymousLogger().info("ERRO AO TENTAR OBTER REFERENCIA PARA ACTIONBAR");
        }
    }
    private void criaTabuleiro(){
        mTabuleiro = new Tabuleiro(mGrade);
        mTabuleiroView.init(mTabuleiro);
    }
    public int dpToPx(int dp) {
        float density = this.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }
    public int pxToDp(int px){
        float density = this.getResources()
                .getDisplayMetrics().density;
        return Math.round((float)px/density);
    }
    //teste
    private void gradeInit(){
        ArrayList<String> palavras;
        switch (Dificuldades.fromString(mDificuldade)){
            case FACIL:
                palavras = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.strings_facil)));
                setTitle(getTitle() + " (" + Dificuldades.FACIL.getVal() + ")");
                Log.i(getClass().getSimpleName(),"Dificuldade selecionada: "+Dificuldades.FACIL.getVal());
                break;
            case INTERMEDIARIO:
                palavras = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.strings_facil)));
                setTitle(getTitle() + " (" + Dificuldades.INTERMEDIARIO.getVal() + ")");
                Log.i(getClass().getSimpleName(),"Dificuldade selecionada: "+Dificuldades.INTERMEDIARIO.getVal());
                break;
            case DIFICIL:
                palavras = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.strings_facil)));
                setTitle(getTitle() + " (" + Dificuldades.DIFICIL.getVal() + ")");
                Log.i(getClass().getSimpleName(),"Dificuldade selecionada: "+Dificuldades.DIFICIL.getVal());
                break;
            case MUITO_DIFICIL:
                palavras = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.strings_facil)));
                setTitle(getTitle() + " (" + Dificuldades.MUITO_DIFICIL.getVal() + ")");
                Log.i(getClass().getSimpleName(),"Dificuldade selecionada: "+Dificuldades.MUITO_DIFICIL.getVal());
                break;
            default:
                palavras = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.strings_facil)));
        }
        mGrade.inserePalavras(palavras);
        Log.i(this.getClass().getSimpleName(), mGrade.getInseridas());
        recyclerDicas = (RecyclerView) findViewById(R.id.recycler_dicas);
        mLmanager = new LinearLayoutManager(this);
        mDicasViewAdapter = new DicasViewAdapter( mGrade.getInseridasAsList());
        recyclerDicas.setHasFixedSize(true);
        recyclerDicas.setLayoutManager(mLmanager);
        recyclerDicas.setAdapter(mDicasViewAdapter);
    }

    public static Bundle getDescricoes() {
        return descricoes;
    }
}
