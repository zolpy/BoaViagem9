package br.com.casadocodigo.boaviagem9;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;

public class GastoListActivity extends ListActivity implements
		OnItemClickListener {

	private List<Map<String, Object>> gastos;
	private String dataAnterior = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String[] de = { "data", "descricao", "valor", "categoria" };
		int[] para = { R.id.data, R.id.descricao, R.id.valor, R.id.categoria };

		SimpleAdapter adapter = new SimpleAdapter(this, listarGastos(),
				R.layout.lista_gasto, de, para);

		adapter.setViewBinder(new GastoViewBinder());

		setListAdapter(adapter);
		getListView().setOnItemClickListener(this);
		registerForContextMenu(getListView());
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Map<String, Object> map = gastos.get(position);
		String descricao = (String) map.get("descricao");
		Toast.makeText(this, "Gasto selecionada: " + descricao,
				Toast.LENGTH_SHORT).show();
	}

	private List<Map<String, Object>> listarGastos() {
		gastos = new ArrayList<Map<String, Object>>();

		Map<String, Object> item = new HashMap<String, Object>();
		item.put("data", "04/02/2012");
		item.put("descricao", "Diária Hotel");
		item.put("valor", "R$ 260,00");
		item.put("categoria", R.color.categoria_hospedagem);
		gastos.add(item);

		item = new HashMap<String, Object>();
		item.put("data", "03/02/2012");
		item.put("descricao", "Wifi");
		item.put("valor", "R$ 7,00");
		item.put("categoria", R.color.categoria_outros);
		gastos.add(item);

		item = new HashMap<String, Object>();
		item.put("data", "02/02/2012");
		item.put("descricao", "Táxi Aeroporto - Hotel");
		item.put("valor", "R$ 34,00");
		item.put("categoria", R.color.categoria_transporte);
		gastos.add(item);

		item = new HashMap<String, Object>();
		item.put("data", "02/02/2012");
		item.put("descricao", "Sanduíche");
		item.put("valor", "R$ 19,90");
		item.put("categoria", R.color.categoria_alimentacao);
		gastos.add(item);

		return gastos;
	}

	private class GastoViewBinder implements ViewBinder {

		@Override
		public boolean setViewValue(View view, Object data,
				String textRepresentation) {

			if (view.getId() == R.id.data) {
				if (!dataAnterior.equals(data)) {
					TextView textView = (TextView) view;
					textView.setText(textRepresentation);
					dataAnterior = textRepresentation;
					view.setVisibility(View.VISIBLE);
				} else {
					view.setVisibility(View.GONE);
				}
				return true;
			}

			if (view.getId() == R.id.categoria) {
				Integer id = (Integer) data;
				view.setBackgroundColor(getResources().getColor(id));
				return true;
			}
			return false;
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.gasto_menu, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.remover) {
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
					.getMenuInfo();
			gastos.remove(info.position);
			getListView().invalidateViews();
			dataAnterior = ""; 
			// remover do banco de dados
			return true;
		}
		return super.onContextItemSelected(item);
	}
}
