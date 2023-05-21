class ItcModal {
    #template = '<div class="itc-modal-backdrop"><div class="itc-modal-content"><div class="itc-modal-header"><div class="itc-modal-title">{{title}}</div><span class="itc-modal-btn-close" title="Закрыть">×</span></div><div class="itc-modal-body">{{content}}</div>{{footer}}</div></div>';
    // ...
}


const modal = new ItcModal({
    title: 'Заголовок',
    content: '<div>Содержимое модального окна...</div>',
    footerButtons: [
      { class: 'btn btn-close', text: 'Закрыть', action: 'close' },
    ]
});