package br.com.casadocodigo.boaviagem9;

import android.os.Bundle;
import br.com.casadocodigo.boaviagem9.domain.Anotacao;

public interface AnotacaoListener {
	void viagemSelecionada(Bundle bundle);
	void anotacaoSelecionada(Anotacao anotacao);
	void novaAnotacao();
}