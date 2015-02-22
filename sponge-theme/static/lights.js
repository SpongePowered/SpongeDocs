$(function () {

  var $ref = $('#syntax-highlighting-ref');

  if (store.get('syntax-highlighting') === 'tomorrow') {
    var href = $ref.attr('href');
    href = href.replace('tomorrow_night.css', 'tomorrow.css');
    $ref.attr('href', href);
  }

  $('.document').css('visibility', 'visible').hide().fadeIn(200);

  $('.lights').on('click', function () {
    var href = $ref.attr('href');
    if (href.indexOf('tomorrow.css') != -1) {
      href = href.replace('tomorrow.css', 'tomorrow_night.css');
      store.set('syntax-highlighting', 'tomorrow_night');
    } else {
      href = href.replace('tomorrow_night.css', 'tomorrow.css');
      store.set('syntax-highlighting', 'tomorrow');
    }
    $ref.attr('href', href);
  });

});
