const dropButton = document.getElementById("dropButton");
const btnBorrar = document.getElementById("btnBorrar");

// Función para controlar el borrado de empleados
function estasSeguro(e) {
    if (confirm("¿Estás seguro que quiere eliminar a este empleado?") == true) {
        return
    }else{
        e.preventDefault();
    }
    
}

// Botón desplegable
if (dropButton != null) {
    dropButton.addEventListener("click", showMenu);
}

function showMenu() {
    document.getElementById("myDropdown").classList.toggle("show");
}

window.addEventListener("click", function (event) {
    if (!event.target.matches('.dropbtn')) {
        const dropdowns = document.getElementsByClassName("dropdown-content");
        for (let i = 0; i < dropdowns.length; i++) {
            const openDropdown = dropdowns[i];
            if (openDropdown.classList.contains('show')) {
                openDropdown.classList.remove('show');
            }
        }
    }
});

// Control de evento de entrada y salida
document.addEventListener('DOMContentLoaded', () => {
  const btnEnter = document.getElementById('btn-enter');
  const btnExit = document.getElementById('btn-exit');

  // Verificar si hay un estado almacenado en localStorage y restaurarlo
  const enterState = localStorage.getItem('btnEnterState');
  const exitState = localStorage.getItem('btnExitState');

  if (enterState === 'disabled' && exitState === 'enabled') {
      btnEnter.disabled = true;
      btnExit.disabled = false;
  } else {
      // Por defecto, asegurémonos de que solo el botón de entrada esté habilitado
      btnEnter.disabled = false;
      btnExit.disabled = true;
  }

  btnEnter.addEventListener('click', () => {
      btnEnter.disabled = true;
      btnExit.disabled = false;
      // Almacenar el estado de los botones en localStorage
      localStorage.setItem('btnEnterState', 'disabled');
      localStorage.setItem('btnExitState', 'enabled');
  });

  btnExit.addEventListener('click', () => {
      btnEnter.disabled = false;
      btnExit.disabled = true;
      // Almacenar el estado de los botones en localStorage
      localStorage.setItem('btnEnterState', 'enabled');
      localStorage.setItem('btnExitState', 'disabled');
  });
});

//filtros
function filtrarFichas() {
    var select, option, table, tr, td, i, txtValue;
    select = document.getElementById("empleadoFiltro");
    option = select.value.toUpperCase();
    table = document.getElementById("fichaTabla");
    tr = table.getElementsByTagName("tr");
    for (i = 1; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[0];
        if (td) {
            txtValue = td.textContent || td.innerText;
            if (option === "" || txtValue.toUpperCase().indexOf(option) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }       
    }
}