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
                scope : me,
                handler : function()
                {
                    Ext.ComponentQuery.query("panelCalendario")[0].fireEvent('eventasignaaula', me, me.rec);
                }
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
Extensible.calendar.data.EventMappings.FechaDetalleManualInt =
{
    name : 'FechaDetalleManualInt',
    mapping : 'fecha_detalle_manual_int',
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
            listeners :
            {
                'change' :
                {
                    fn : function()
                    {
                        if (this.detalleManualField.getValue())
                        {
                            this.detalleManualFechas.show();
                            this.detalleManualFechas.checkBoxes();

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
            anchor : '90%',
            nameCheckbox : Extensible.calendar.data.EventMappings.FechaDetalleManual.name,
            nameHidden : Extensible.calendar.data.EventMappings.FechaDetalleManualInt.name
        });

        this.detalleClases = Ext.create('Event.form.DetalleClases',
        {
            anchor : '90%'
        });

        this.notaExamenes = Ext.create('Ext.form.field.Display',
        {
            value : "[*] Període d'exàmens",
            fieldCls : 'form-legend-exams'
        });

        this.panelAviso = Ext.create('Event.form.PanelInfo',
        {
            anchor : '90%'
        });

        var leftFields = [ this.titleField, this.dateRangeField, this.dateRepeatField, this.detalleManualField, this.detalleManualFechas, this.detalleClases, this.posteoDetalleField,
                this.notaExamenes, this.panelAviso ];

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

        me.getEl().mask('Carregant dades...');

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

        // if (!rec.data[Extensible.calendar.data.EventMappings.EndRepNumberComp])
        // {
        // this.down('numberfield').setValue(1);
        // }

        if (me.calendarField)
        {
            me.calendarField.setValue(rec.data[EventMappings.CalendarId.name]);
        }
        me.setTitle(me.titleTextEdit);

        me.dateRepeatField.setRepetirCadaValue(rec.data[EventMappings.RepetirCada.name]);

        me.form.getFields().each(function(item)
        {
            item.resetOriginalValue();
        });

        me.titleField.focus();

        var numRepeticiones = rec.data[EventMappings.EndRepNumberComp.name];
        if (rec.data[EventMappings.DetalleManual.name] == 'true')
        {
            numRepeticiones = '';
        }
        me.getDetalleClasesDocencia(rec.data[EventMappings.EventId.name], numRepeticiones);
    },

    // private
    onSave : function()
    {
        var me = this;

        if (!me.getForm().isValid())
        {
            return;
        }

        if (!me.updateRecord(me.activeRecord))
        {
            return;
        }

        me.activeRecord.store.on(
        {
            update : function(store, record)
            {
                var me = this;
                me.record = record;

                if (!this.activeRecord)
                    return;
                this.down('button[name=close]').setText('Tancar');

                var waitForLoading = function()
                {
                    me.loadRecord(me.record);
                };

                var task = new Ext.util.DelayedTask(waitForLoading);
                me.getEl().mask('Carregant dades...');
                task.delay(500);
            },
            scope : me,
            single : true
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

    onCancel : function()
    {
        var me = this;
        if (this.getForm().isDirty())
        {
            Ext.Msg.confirm('Dades sense guardar', 'Tens dades sense guardar en el event, estàs segur de voler tancar la edició?', function(btn, text)
            {
                if (btn == 'yes')
                {
                    me.activeRecord.store.load();
                    me.cleanup(true);
                    me.fireEvent('eventcancel', me, me.activeRecord);
                }
            });
        }
        else
        {
            me.activeRecord.store.load();
            me.cleanup(true);
            me.fireEvent('eventcancel', me, me.activeRecord);
        }
    },

    getDetalleClasesDocencia : function(eventoId, numeroRepeticiones)
    {
        var me = this;
        Ext.Ajax.request(
        {
            url : '/hor/rest/calendario/eventos/docencia/' + eventoId,
            method : 'GET',
            success : function(response)
            {
                var clases = Ext.JSON.decode(response.responseText).data;

                var numClases = me.detalleClases.actualizarDetalleClases(clases);
                me.detalleClases.show();

                // Si se ha establecido el número de repeticiones, comprobamos que coincide con el
                // número de clases

                if (numeroRepeticiones && numeroRepeticiones != numClases)
                {
                    me.down('panelinfo').showAviso('AVIS: Les repeticions no coincideixen amb el nombre de classes. Es possible que alguna classe haja coincidit amb un dia festiu.');
                }
                else
                {
                    me.down('panelinfo').dropAviso();
                }

                me.detalleManualFechas.addPosiblesFechas(clases);

                if (me.detalleManualField.getValue())
                {
                    me.detalleManualFechas.checkBoxes();
                }

                me.getEl().unmask();
            },
            failure : function()
            {
                me.getEl().unmask();
            }
        });
    },

    updateRecord : function(record)
    {
        var fields = record.fields, values = this.getForm().getValues(), EventMappings = Extensible.calendar.data.EventMappings, name, obj = {};

        fields.each(function(f)
        {
            name = f.name;
            if (name in values)
            {
                obj[name] = values[name];
            }
        });

        var dates = this.dateRangeField.getValue(), allday = obj[EventMappings.IsAllDay.name] = dates[2],
        // Clear times for all day events so that they are stored consistently
        startDate = allday ? Extensible.Date.clearTime(dates[0]) : dates[0], endDate = allday ? Extensible.Date.clearTime(dates[1]) : dates[1], singleDayDurationConfig =
        {
            days : 1
        };

        // The full length of a day based on the minimum event time resolution:
        singleDayDurationConfig[Extensible.calendar.data.EventModel.resolution] = -1;

        obj[EventMappings.StartDate.name] = startDate;

        // If the event is all day, calculate the end date as midnight of the day after the end
        // date minus 1 unit based on the EventModel resolution, e.g. 23:59:00 on the end date
        obj[EventMappings.EndDate.name] = allday ? Extensible.Date.add(endDate, singleDayDurationConfig) : endDate;

        if (EventMappings.Duration)
        {
            obj[EventMappings.Duration.name] = Extensible.Date.diff(startDate, obj[EventMappings.EndDate.name], Extensible.calendar.data.EventModel.resolution);
        }

        if (obj[Extensible.calendar.data.EventMappings.FechaFinRadio.name] == "R")
        {
            obj[Extensible.calendar.data.EventMappings.EndDateRepComp.name] = "";

        }
        else if (obj[Extensible.calendar.data.EventMappings.FechaFinRadio.name] == "D")
        {
            obj[Extensible.calendar.data.EventMappings.EndRepNumberComp.name] = "";
        }
        else
        {
            obj[Extensible.calendar.data.EventMappings.EndDateRepComp.name] = "";
            obj[Extensible.calendar.data.EventMappings.EndRepNumberComp.name] = "";
        }

        var fechas = obj[Extensible.calendar.data.EventMappings.FechaDetalleManual.name];
        obj[Extensible.calendar.data.EventMappings.FechaDetalleManualInt.name] = Ext.JSON.encode(fechas);

        record.set(obj);
        return record.dirty;
    },

});
