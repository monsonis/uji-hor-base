var dataHoras = new Array();

var j = 0;
for ( var i = 8; i <= 22; i++)
{
    var name = i + ':00';
    if ((i + '').length == 1)
    {
        name = '0' + name;
    }
    var id = "1/1/1 " + name;
    
    dataHoras[j++] = [ id, name ];
}

Ext.define('HOR.store.StoreHoras',
{
    extend : 'Ext.data.ArrayStore',
    fields : [ 'id', 'name' ],
    data : dataHoras
});