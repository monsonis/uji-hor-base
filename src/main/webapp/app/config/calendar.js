Extensible.calendar.menu.Event.override(
{
    buildMenu : function()
    {
        var me = this;

        if (me.rendered)
        {
            return;
        }
        Ext.apply(me,
        {
            items : [
            {
                text : me.editDetailsText,
                iconCls : 'extensible-cal-icon-evt-edit',
                scope : me,
                handler : function()
                {
                    me.fireEvent('editdetails', me, me.rec, me.ctxEl);
                }
            },
            {
                text : 'Assignar aula',
                iconCls : 'extensible-cal-icon-evt-edit',
                menu : me.dateMenu

            },
            {
                text : 'Assignar a circuit',
                iconCls : 'extensible-cal-icon-evt-edit',
                menu : me.copyMenu

            }, '-',
            {
                text : 'Dividir',
                iconCls : 'extensible-cal-icon-evt-copy',
                scope : me,
                handler : function()
                {
                    Ext.ComponentQuery.query("panelCalendario")[0].fireEvent('eventdivide', me, me.rec);
                }
            },
            {
                text : me.deleteText,
                iconCls : 'extensible-cal-icon-evt-del',
                scope : me,
                handler : function()
                {
                    me.fireEvent('eventdelete', me, me.rec, me.ctxEl);
                }
            } ]
        });
    },

    showForEvent : function(rec, el, xy)
    {
        var me = this;
        me.rec = rec;
        me.ctxEl = el;
        me.showAt(xy);
    }
});

Extensible.calendar.form.EventWindow.override(
{
    getFormItemConfigs : function()
    {
        var items = [
        {
            xtype : 'textfield',
            itemId : this.id + '-title',
            name : Extensible.calendar.data.EventMappings.Title.name,
            fieldLabel : this.titleLabelText,
            anchor : '100%',
            readOnly : true
        },/*
             * { xtype: 'extensible.daterangefield', itemId: this.id + '-dates', name: 'dates',
             * anchor: '95%', singleLine: true, fieldLabel: this.datesLabelText }
             */
        {
            xtype : 'daterangefield',
            itemId : this.id + '-dates',
            name : 'dates',
            anchor : '95%',
            fieldLabel : this.datesLabelText
        } ];

        if (this.calendarStore)
        {
            items.push(
            {
                xtype : 'extensible.calendarcombo',
                itemId : this.id + '-calendar',
                name : Extensible.calendar.data.EventMappings.CalendarId.name,
                anchor : '100%',
                fieldLabel : this.calendarLabelText,
                store : this.calendarStore,
                readOnly : true
            });
        }

        return items;
    },
});

Extensible.calendar.data.EventMappings.RepetirCada =
{
    name : 'RepetirCada',
    mapping : 'repetir_cada'
};
Extensible.calendar.data.EventMappings.StartDateRep =
{
    name : 'StartDateRep',
    mapping : 'start_date_rep'
};
Extensible.calendar.data.EventMappings.NoEnd =
{
    name : 'NoEnd',
    mapping : 'no_end'
};
Extensible.calendar.data.EventMappings.EndRepNumberComp =
{
    name : 'EndRepNumberComp',
    mapping : 'end_rep_number_comp',
    type : 'int'
};
Extensible.calendar.data.EventMappings.FechaFinRadio =
{
    name : 'FechaFinRadio',
    mapping : 'seleccionRadioFechaFin',
    type : 'string'
};
Extensible.calendar.data.EventMappings.EndDateRepComp =
{
    name : 'EndDateRepComp',
    mapping : 'end_date_rep_comp',
};
Extensible.calendar.data.EventMappings.DetalleManual =
{
    name : 'DetalleManual',
    mapping : 'detalle_manual',
};
Extensible.calendar.data.EventMappings.FechaDetalleManual =
{
    name : 'FechaDetalleManual',
    mapping : 'fecha_detalle_manual',
};
Extensible.calendar.data.EventMappings.PosteoDetalle =
{
    name : 'PosteoDetalle',
    mapping : 'posteo_detalle',
    type : 'int',
    defaultValue : 0
};
Extensible.calendar.data.EventModel.reconfigure();

Extensible.calendar.form.EventDetails.override(
{
    // private
    initComponent : function()
    {
        this.addEvents(
        {

            /**
             * @event eventadd Fires after a new event is added
             * @param {Extensible.calendar.form.EventDetails}
             *            this
             * @param {Extensible.calendar.data.EventModel}
             *            rec The new {@link Extensible.calendar.data.EventModel record} that was
             *            added
             */
            eventadd : true,

            /**
             * @event eventupdate Fires after an existing event is updated
             * @param {Extensible.calendar.form.EventDetails}
             *            this
             * @param {Extensible.calendar.data.EventModel}
             *            rec The new {@link Extensible.calendar.data.EventModel record} that was
             *            updated
             */
            eventupdate : true,

            /**
             * @event eventdelete Fires after an event is deleted
             * @param {Extensible.calendar.form.EventDetails}
             *            this
             * @param {Extensible.calendar.data.EventModel}
             *            rec The new {@link Extensible.calendar.data.EventModel record} that was
             *            deleted
             */
            eventdelete : true,

            /**
             * @event eventcancel Fires after an event add/edit operation is canceled by the user
             *        and no store update took place
             * @param {Extensible.calendar.form.EventDetails}
             *            this
             * @param {Extensible.calendar.data.EventModel}
             *            rec The new {@link Extensible.calendar.data.EventModel record} that was
             *            canceled
             */
            eventcancel : true
        });

        this.trackResetOnLoad = true;
        
        this.titleField = Ext.create('Ext.form.TextField',
        {
            fieldLabel : this.titleLabelText,
            name : Extensible.calendar.data.EventMappings.Title.name,
            anchor : '90%',
            readOnly : true
        });
        this.dateRangeField = Ext.create('Event.form.field.DateRange',
        {
            fieldLabel : this.datesLabelText,
            anchor : '90%',
            listeners :
            {
                'change' : Ext.bind(this.onDateChange, this)
            }
        });

        this.dateRepeatField = Ext.create('Event.form.field.DateRepeat', {});
        this.posteoDetalleField = Ext.create('Event.form.field.PosteoDetalle', {});
        this.detalleManualField = Ext.create('Ext.form.field.Checkbox',
        {
            boxLabel : 'Detall manual',
            name : Extensible.calendar.data.EventMappings.DetalleManual.name,
            nameCheckbox : Extensible.calendar.data.EventMappings.FechaDetalleManual.name,
            listeners :
            {
                'change' :
                {
                    fn : function()
                    {
                        if (this.detalleManualField.getValue())
                        {
                            var data = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ];
                            var values = [ '01/10/2012', '02/10/2012', '03/10/2012', '04/10/2012', '05/10/2012', '06/10/2012', '07/10/2012', '08/10/2012', '09/10/2012', '10/10/2012' ];
                            this.detalleManualFechas.addPosiblesFechas(data, values);
                            this.detalleManualFechas.show();
                            this.detalleManualFechas.checkAllBoxes();

                            this.dateRangeField.disableFields();
                            this.dateRepeatField.disableFields();
                        }
                        else
                        {
                            this.detalleManualFechas.hide();

                            this.dateRangeField.enableFields();
                            this.dateRepeatField.enableFields();
                        }
                    },
                    scope : this
                }
            }
        });

        this.detalleManualFechas = Ext.create('Event.form.field.DetalleManual',
        {
            anchor : '90%'
        });

        this.detalleClases = Ext.create('Event.form.DetalleClases',
        {
            anchor : '90%'
        });

        var leftFields = [ this.titleField, this.dateRangeField, this.dateRepeatField, this.detalleManualField, this.detalleManualFechas, this.detalleClases, this.posteoDetalleField ];

        if (this.calendarStore)
        {
            this.calendarField = Ext.create('Extensible.calendar.form.field.CalendarCombo',
            {
                store : this.calendarStore,
                fieldLabel : this.calendarLabelText,
                name : Extensible.calendar.data.EventMappings.CalendarId.name,
                anchor : '90%',
                readOnly : true
            });
            leftFields.splice(2, 0, this.calendarField);
        }
        ;

        this.items = [
        {
            id : this.id + '-left-col',
            columnWidth : this.colWidthLeft,
            layout : 'anchor',
            fieldDefaults :
            {
                labelWidth : this.labelWidth
            },
            border : false,
            items : leftFields
        } /*
             * , { id : this.id + '-right-col', columnWidth : this.colWidthRight, layout : 'anchor',
             * fieldDefaults : { labelWidth : this.labelWidthRightCol || this.labelWidth }, border :
             * false, items : rightFields }
             */];

        this.fbar = [
        {
            text : this.saveButtonText,
            scope : this,
            handler : this.onSave
        },
        {
            text : 'Tancar',
            name : 'close',
            scope : this,
            handler : this.onCancel
        } ];

        this.addCls('ext-evt-edit-form');

        // this.callParent(arguments);

        this.on('dirtychange', function(form, isDirty)
        {
            if (isDirty)
            {
                this.down('button[name=close]').setText('Tancar sense guardar');
            }
            else
            {
                this.down('button[name=close]').setText('Tancar');
            }
        });

        this.superclass.initComponent.call(this);
    },

    loadRecord : function(rec)
    {

        var me = this, EventMappings = Extensible.calendar.data.EventMappings;

        // Simulamos datos en los nuevos campos
        // rec.data[EventMappings.ModificaDetalle.name] = "on";
        // rec.data[EventMappings.EndRepNumberComp.name] = 6;
        // rec.data[EventMappings.EndDateRepComp.name] = '7/9/2012';

        if (!rec.data[EventMappings.RepetirCada.name])
        {
            rec.data[EventMappings.RepetirCada.name] = '1';
        }

        me.form.reset().loadRecord.apply(me.form, arguments);
        me.activeRecord = rec;
        me.dateRangeField.setValue(rec.data);

        me.posteoDetalleField.setValue(1);

        if (rec.data[EventMappings.EndRepNumberComp.name])
        {
            this.down('radiogroup[name=grupoDuracion] radio[inputValue=R]').setValue(true);
            me.dateRepeatField.setNumeroRepeticionesValue(rec.data[EventMappings.EndRepNumberComp.name]);
        }
        else if (rec.data[EventMappings.EndDateRepComp.name])
        {
            this.down('radiogroup[name=grupoDuracion] radio[inputValue=D]').setValue(true);
            me.dateRepeatField.setRepetirHastaValue(rec.data[EventMappings.EndDateRepComp.name]);
        }
        else
        {
            this.down('radiogroup[name=grupoDuracion] radio[inputValue=F]').setValue(true);
        }

        if (me.calendarField)
        {
            me.calendarField.setValue(rec.data[EventMappings.CalendarId.name]);
        }
        me.setTitle(me.titleTextEdit);

        me.dateRepeatField.setRepetirCadaValue(rec.data[EventMappings.RepetirCada.name]);

        // Using setValue() results in dirty fields, so we reset the field state
        // after loading the form so that the current values are the "original" values
        me.form.getFields().each(function(item)
        {
            item.resetOriginalValue();
        });

        me.titleField.focus();

        me.getDetalleClases(rec.data[EventMappings.EventId.name]);
    },

    // private
    onSave : function()
    {
        var me = this;

        if (!me.form.isValid())
        {
            return;
        }

        if (!me.updateRecord(me.activeRecord))
        {
            me.onCancel();
            return;
        }
        
        me.activeRecord.store.on(
        {
            update : function()
            {
                if (!this.activeRecord)
                    return;
                this.down('button[name=close]').setText('Tancar');
                me.getForm().reset();
                me.getForm().loadRecord(me.activeRecord);
                var fields = me.getForm().getFields();
                Ext.Array.each( fields.items, function(field) {
                    field.resetOriginalValue();
                });
                this.getDetalleClases(this.activeRecord.data[Extensible.calendar.data.EventMappings.EventId.name]);
            },
            scope : me
        });

        if (me.activeRecord.phantom)
        {
            me.fireEvent('eventadd', me, me.activeRecord);
            return;
        }
        else
        {
            me.fireEvent('eventupdate', me, me.activeRecord);
        }
    },

    onCancel: function() {
      this.activeRecord.store.load();
      this.cleanup(true);
      this.fireEvent('eventcancel', this, this.activeRecord);
    },
    
    getDetalleClases : function(eventoId)
    {
        var me = this;
        Ext.Ajax.request(
        {
            url : '/hor/rest/calendario/eventos/detalle/' + eventoId,
            method : 'GET',
            success : function(response)
            {
                var eventos = Ext.JSON.decode(response.responseText).data;
                var clases = new Array();
                for ( var i = 0; i < eventos.length; i++)
                {
                    // var fecha = eventos[i].start;

                    var fecha = Ext.Date.parse(eventos[i].start, "Y-m-d\\TH:i:s");

                    clases[i] = Ext.Date.format(fecha, "d/m/Y");
                }

                me.detalleClases.actualizarDetalleClases(clases);
                me.detalleClases.show();
            }
        });
    }

});
