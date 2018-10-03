package login.com.example.filipe.firebaseloginapp.Interface;

import android.view.View;


//EXEMPLO DE PADRÂO DE PROJETO:
//Padrões de criação
// Factory Method -
// Esse padrão define uma interface para a criação de um objeto,
// deixando que as subclasses fiquem responsáveis por decidir qual classe instanciar.
public interface ItemClickListener {
    void onClick(View view, int position,boolean isLongClick);
}
