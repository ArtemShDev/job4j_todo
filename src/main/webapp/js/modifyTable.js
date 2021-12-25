$(document).ready(loadItemsDrawTable());

function loadItemsDrawTable() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/job4j_todo/items?showAll='+$('#showAll').prop('checked'),
        dataType: 'json'
    }).done(function (data) {
        for (var item of data) {
            addRowTheTable(item);
        }
    }).fail(function (err) {
        console.log(err);
    });
}

function addRowTheTable(item) {
    var tbody = document.getElementById('table_body');
    let row = document.createElement('tr');
    row.setAttribute("id", "id_" + item.id);
    let row_th = document.createElement('th');
    row_th.setAttribute("class", "table-active");
    row_th.innerHTML = item.id;
    row.appendChild(row_th);

    let row_td = document.createElement('td');
    row_td.setAttribute("class", "table-active");
    row_td.innerHTML = item.description;

    row.appendChild(row_td);
    let row_cell = document.createElement('td');
    let row_input = document.createElement('input');
    row_input.setAttribute("type", "checkbox");
    row_input.setAttribute("id", "done");
    row_input.setAttribute("idDB", item.id);
    row_input.setAttribute("onchange", "selectDone("+item.id+")");
    if (item.done) {
        row.setAttribute("bgcolor", "#80cb64");
        row_input.setAttribute("disabled", "disabled");
        row_input.setAttribute("checked", "checked");
    }
    row_cell.appendChild(row_input);
    row.appendChild(row_cell);
    tbody.appendChild(row);
}

function selectDone(paramId) {
    let elems = document.getElementById("id_" + paramId).querySelectorAll('.table-active');
    var dataJ = JSON.stringify({id: Number (elems[0].innerHTML), description: elems[1].innerHTML, done: true});
    console.log(dataJ);
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/job4j_todo/itemsModify?id=' + paramId,
        data: dataJ,
        dataType: 'json'
    }).done(function (data) {
        showAllIt();
    }).fail(function (err) {
        console.log(err);
    });
}

function clearTable() {
    var elem = document.getElementById('table_body');
    elem.parentNode.removeChild(elem);
    var tb = document.createElement('tbody');
    tb.setAttribute("id", "table_body");
    var elemTab = document.getElementById('items_tab');
    elemTab.appendChild(tb);
    return false;
}

function addItem() {
    if ($('#formNameItem').val() === '') {
        alert('Please, ' + $('#formNameItem').attr('placeholder'));
        return false;
    }
    if ($('#formDescItem').val() === '') {
        alert('Please, ' + $('#formDescItem').attr('placeholder'));
        return false;
    }
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/job4j_todo/items?descItem=' + $('#formDescItem').val(),
        data: "",
        dataType: 'json'
    }).done(function (data) {
        addRowTheTable(data);
    }).fail(function (err) {
        console.log(err);
    });
    return true;
}

function showAllIt() {
    clearTable();
    loadItemsDrawTable();
    return false;
}