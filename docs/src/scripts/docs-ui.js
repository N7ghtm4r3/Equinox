function initMobileNavigation() {
  const sidebar = document.querySelector('.sidebar-entries sl-sidebar-state-persist');
  const drawer = document.getElementById('drawer');
  const target = drawer?.querySelector('.links-docs');
  if (!sidebar || !target) return;

  // Lucode's drawer only renders direct links; reuse the complete sidebar tree.
  const navigation = document.createElement('nav');
  navigation.className = 'equinox-mobile-nav';
  navigation.setAttribute('aria-label', 'Documentation');
  for (const entry of sidebar.children) navigation.append(entry.cloneNode(true));
  navigation.querySelectorAll('script, sl-sidebar-restore').forEach(element => element.remove());
  target.replaceChildren(navigation);
  navigation.addEventListener('click', event => {
    if (event.target instanceof Element && event.target.closest('a')) drawer.hidePopover();
  });
}

function initDocsUI() {
  initMobileNavigation();
  const groups = [...document.querySelectorAll('.equinox-tabs')];
  function activate(group, label, focus = false) {
    const tabs = [...group.querySelector('[role="tablist"]').children];
    const target = tabs.find(tab => tab.textContent === label);
    if (!target) return;
    for (const tab of tabs) {
      const selected = tab === target;
      tab.setAttribute('aria-selected', String(selected));
      tab.tabIndex = selected ? 0 : -1;
      document.getElementById(tab.getAttribute('aria-controls')).hidden = !selected;
    }
    if (focus) target.focus();
  }
  for (const group of groups) {
    const tabs = [...group.querySelector('[role="tablist"]').children];
    activate(group, tabs[0].textContent);
    for (const tab of tabs) {
      tab.addEventListener('click', () => {
        for (const sibling of groups) activate(sibling, tab.textContent);
      });
      tab.addEventListener('keydown', event => {
        const offset = event.key === 'ArrowRight' ? 1 : event.key === 'ArrowLeft' ? -1 : 0;
        let index = offset ? (tabs.indexOf(tab) + offset + tabs.length) % tabs.length : -1;
        if (event.key === 'Home') index = 0;
        if (event.key === 'End') index = tabs.length - 1;
        if (index < 0) return;
        event.preventDefault();
        for (const sibling of groups) activate(sibling, tabs[index].textContent);
        activate(group, tabs[index].textContent, true);
      });
    }
  }
  const dialog = document.createElement('dialog');
  dialog.className = 'equinox-lightbox';
  const close = document.createElement('button');
  close.textContent = 'Close image';
  const enlarged = document.createElement('img');
  dialog.append(close, enlarged);
  document.body.append(dialog);
  close.addEventListener('click', () => dialog.close());
  dialog.addEventListener('click', event => { if (event.target === dialog) dialog.close(); });
  for (const img of document.querySelectorAll('.sl-markdown-content img')) {
    if (img.closest('a') || img.src.includes('img.shields.io')) continue;
    img.dataset.zoomable = '';
    img.tabIndex = 0;
    img.setAttribute('role', 'button');
    img.setAttribute('aria-label', `Enlarge ${img.alt || 'image'}`);
    const open = () => { enlarged.src = img.src; enlarged.alt = img.alt; dialog.showModal(); };
    img.addEventListener('click', open);
    img.addEventListener('keydown', event => { if (['Enter', ' '].includes(event.key)) { event.preventDefault(); open(); } });
  }
}
initDocsUI();
