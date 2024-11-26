function openModal(target,redirectTarget) {
    console.log(target);

    fetch(`/tabulated-operations/createForm?target=${target}&redirectTarget=${redirectTarget}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка загрузки модального окна');
            }
            return response.text();
        })
        .then(html => {
            document.getElementById('modalContent').innerHTML = html;
            document.getElementById('modal').style.display = 'block';
        })
        .catch(error => {
            console.error(error);
        });
}
function closeModal() {
    console.log("ppppp");
    document.getElementById('modal').style.display = 'none';
}

function submitForm(event) {
    const form = event.target;

    const target = form.querySelector('input[name="target"]').value; // Извлекаем значение hidden-поля
    const redirectTarget = form.querySelector('input[name="redirectTarget"]').value;

    event.preventDefault(); // Предотвращаем стандартное поведение формы
    const formData = new FormData(form);

    const url = `/tabulated-operations/generateTable`;

    fetch(url, {
        method: 'POST',
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(data => {
                    // Если ответ неудачный, пытаемся получить данные ошибки
                    throw new Error(data.error || "Ошибка при генерации таблицы.");
                });
            }
            return response.text(); // Если успешный, получаем HTML
        })
        .then(html => {
            // Успешная обработка ответа
            document.getElementById('modalContent').innerHTML = html;
        })
        .catch(error => {
            console.error('Ошибка при генерации таблицы:', error);

            // Отображение ошибки в модальном окне
            const errorForm = document.getElementById('errorForm');
            if (errorForm) {
                errorForm.style.display = 'block';
                document.getElementById('errorMessage').textContent = error.message;
            }
        });
}

let modalTarget = '';
let selectFunctionId = null;

function openFunctionList(target) {
    modalTarget = target;
    document.getElementById('modalFunction').style.display = 'block';
}
function closeFunctionList() {
    document.getElementById('modalFunction').style.display = 'none';
}

function selectFunction(row, id, target) {

    console.log(row, id, target);
    console.log(target);
    // Снимаем выделение со всех строк
    const rows = document.querySelectorAll('.function-row');
    rows.forEach(r => r.classList.remove('selected'));

    // Выделяем выбранную строку
    row.classList.add('selected');

    selectFunctionId = id;

    console.log(selectFunctionId);
}

function loadFunction() {
    if (!selectFunctionId || !modalTarget) {
        console.error("Выберите функцию и цель перед загрузкой.");
        return;
    }

    const url = `/tabulated-operations/load?target=${encodeURIComponent(modalTarget)}&id=${encodeURIComponent(selectFunctionId)}`;

    fetch(url, { method: 'POST' })
        .then(response => {
            if (!response.ok) {
                return response.json();
            }
            return null;
        })
        .then(data => {
            if (data && data.error) {
                const errorForm = document.getElementById('errorForm');
                if (errorForm) {
                    closeFunctionList();
                    errorForm.style.display = 'block';
                    document.getElementById('errorMessage').textContent = data.error;
                }
            } else {
                closeFunctionList();
                window.location.reload();
            }
        })
        .catch(error => {
            console.error('Ошибка при загрузке функции');
        });
}


let saveTarget = '';
function openSave(target){
    saveTarget = target;
    document.getElementById('modalSave').style.display = 'block';
}

function closeSave() {
    document.getElementById('modalSave').style.display = 'none';
}

function saveFunction(event) {

    event.preventDefault();
    const funcName = document.getElementById('funcName').value;

    fetch(`/tabulated-operations/save?target=${encodeURIComponent(saveTarget)}&funcName=${encodeURIComponent(funcName)}`, {
        method: 'POST'
    })
    window.location.reload();
}

function openAnother(redirectTarget,target){
    fetch(`/tabulated-function-mathfunc/modal?redirectTarget=${encodeURIComponent(redirectTarget)}&target=${encodeURIComponent(target)}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка загрузки модального окна');
            }
            return response.text();
        })
        .then(html => {
            document.getElementById('modalContentAnother').innerHTML = html;
            document.getElementById('modalAnother').style.display = 'block';
        })
        .catch(error => {
            console.error(error);
        });
}

function closeAnother(){
    document.getElementById('modalAnother').style.display = 'none';
}

function close(){
    document.getElementById('modal').style.display = 'none';
}