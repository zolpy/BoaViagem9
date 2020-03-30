package br.com.casadocodigo.boaviagem9;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import java.util.Date;

import br.com.casadocodigo.boaviagem9.dao.BoaViagemDAO;
import br.com.casadocodigo.boaviagem9.domain.Anotacao;
import br.com.casadocodigo.boaviagem9.domain.Viagem;
import br.com.casadocodigo.boaviagem9.fragment.AnotacaoFragment;
import br.com.casadocodigo.boaviagem9.fragment.AnotacaoListFragment;
import br.com.casadocodigo.boaviagem9.fragment.ViagemListFragment;

public class AnotacaoActivity extends FragmentActivity implements AnotacaoListener{

	private boolean tablet = true;
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.anotacoes);
		
		View view = findViewById(R.id.fragment_unico);
		
		mock();
		
		if(view != null){
			tablet = false;
			
			ViagemListFragment fragment = new ViagemListFragment();
			fragment.setArguments(bundle);

			FragmentManager manager = getSupportFragmentManager();
			FragmentTransaction transaction = manager.beginTransaction();
			transaction.replace(R.id.fragment_unico, fragment);
			transaction.addToBackStack(null);
			transaction.commit();
		}
	}
	
	private void mock(){
		BoaViagemDAO dao = new BoaViagemDAO(this);
		for (int i = 0; i < 20; i++) {
			Viagem viagem = new Viagem();
			viagem.setDestino("Destino " + (i+1));
			viagem.setDataChegada(new Date());
			viagem.setDataSaida(new Date());
			dao.inserir(viagem);
		}
		dao.close();
	}
	
	@Override
	public void viagemSelecionada(Bundle bundle) {
		
		FragmentManager manager = getSupportFragmentManager();
		AnotacaoListFragment fragment;
		
		if(tablet){
			fragment = (AnotacaoListFragment) manager
							.findFragmentById(R.id.fragment_anotacoes);
			fragment.listarAnotacoesPorViagem(bundle);
			
		}else{
			fragment = new AnotacaoListFragment();
			fragment.setArguments(bundle);
			
			manager.beginTransaction()
						.replace(R.id.fragment_unico, fragment)
						.addToBackStack(null)
						.commit();
		}
	}

	@Override
	public void anotacaoSelecionada(Anotacao anotacao) {
		FragmentManager manager = getSupportFragmentManager();
		AnotacaoFragment fragment;
		
		if(tablet){
			fragment = (AnotacaoFragment) manager
							.findFragmentById(R.id.fragment_anotacao);
			fragment.prepararEdicao(anotacao);
			
		}else{
			fragment = new AnotacaoFragment();
			fragment.setAnotacao(anotacao);
			
			manager.beginTransaction()
						.replace(R.id.fragment_unico, fragment)
						.addToBackStack(null)
						.commit();
		}
	}

	@Override
	public void novaAnotacao() {
		FragmentManager manager = getSupportFragmentManager();
		AnotacaoFragment fragment;
		
		if(tablet){
			fragment = (AnotacaoFragment) manager
							.findFragmentById(R.id.fragment_anotacao);
			fragment.criarNovaAnotacao();
			
		}else{
			fragment = new AnotacaoFragment();
			manager.beginTransaction()
						.replace(R.id.fragment_unico, fragment)
						.addToBackStack(null)
						.commit();
		}
	}
}
