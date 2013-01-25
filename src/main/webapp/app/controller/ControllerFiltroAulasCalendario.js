Ext.define('HOR.controller.ControllerFiltroAulasCalendario',
{
    extend : 'Ext.app.Controller',
    stores : [ 'StoreCentros', 'StoreEdificios', 'StoreTiposAula', 'StorePlantasEdificio'],
    refs : [
    {
        selector : 'filtroAulas',
        ref : 'filtroAulas'
    },
    {
        selector : 'selectorAulas',
        ref : 'selectorAulas'
    } ],
    
    init : function()
    {
        this.control(
        {
            'filtroAulas combobox[name=centro]' :
             {
                select : this.onCentroSelected
             },
             'filtroAulas combobox[name=semestre]' :
             {
                 select : this.onSemestreSelected
             },
             'filtroAulas combobox[name=edificio]' :
             {
                 select : this.onEdificioSelected
             }
        });
    },

    onCentroSelected : function()
    {
        var semestre = this.getFiltroAulas().down('combobox[name=semestre]');
        
        semestre.clearValue();
        this.getFiltroAulas().down('combobox[name=edificio]').clearValue();
        this.getFiltroAulas().down('combobox[name=tipoAula]').clearValue();
        this.getFiltroAulas().down('combobox[name=planta]').clearValue();
        
        this.getStoreEdificiosStore().removeAll();
        this.getStoreTiposAulaStore().removeAll();
        this.getStorePlantasEdificioStore().removeAll();
        
        var store = semestre.getStore();
        
        if (store.count() == 0)
        {
            store.loadData([ [ '1', '1' ], [ '2', '2' ] ]);
        }
    },
    
    onSemestreSelected : function()
    {
        this.getFiltroAulas().down('combobox[name=edificio]').clearValue();
        this.getFiltroAulas().down('combobox[name=tipoAula]').clearValue();
        this.getFiltroAulas().down('combobox[name=planta]').clearValue();
        
        this.getStoreTiposAulaStore().removeAll();
        this.getStorePlantasEdificioStore().removeAll();
        
        var centro = this.getFiltroAulas().down('combobox[name=centro]').getValue();
        
        var store = this.getStoreEdificiosStore();
        
        store.load(
        {
            params :
            {
                centroId : centro
            },
            scope : this
        });
        
        fixLoadMaskBug(store, this.getFiltroAulas().down('combobox[name=edificio]'));
    },
    
    onEdificioSelected : function()
    {
        this.getFiltroAulas().down('combobox[name=tipoAula]').clearValue();
        this.getFiltroAulas().down('combobox[name=planta]').clearValue();
        
        var centro = this.getFiltroAulas().down('combobox[name=centro]').getValue();
        var edificio = this.getFiltroAulas().down('combobox[name=edificio]').getValue();
        
        var storeTipos = this.getStoreTiposAulaStore();
        var storePlantas = this.getStorePlantasEdificioStore();
        
        storeTipos.load(
        {
           params : 
           {
               centroId : centro,
               edificio : edificio
           },
           scope : this
        });
        
        fixLoadMaskBug(storeTipos, this.getFiltroAulas().down('combobox[name=tipoAula]'));
        
        storePlantas.load(
        {
            params : 
            {
                centroId : centro,
                edificio : edificio
            },
            scope : this
        });
                
        fixLoadMaskBug(storePlantas, this.getFiltroAulas().down('combobox[name=planta]'));
    }
    
});