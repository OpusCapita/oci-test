const cheerio = require('cheerio')
const pug = require('pug');
const renderFormPage = pug.compileFile('./views/hook.pug');

const props = {
  name: "test-hook",
  items: [
    [
      {
        id: 'input-1',
        value: 'input-1',
      },
      {
        id: 'input-2',
        value: 'input-2',
      },
    ],
    [
      {
        id: 'undefined',
        value: 'null',
      },
      {
        id: '0',
        value: 'input-4',
      },
      {
        id: 'input-5',
        value: '1',
      },
    ],
    [
      {
        id: '-4',
        value: 's',
      },
    ]
  ]
}

describe('hook.html',() => {
  test('should render hook.html', () => {
    const $ = cheerio.load(renderFormPage(props));
    Array.from($('div.mb-5')).forEach((item, i) => {
      item.children.forEach((row, j) => {
        expect(row.firstChild.firstChild.data).toBe(props.items[i][j].id);
        expect(row.lastChild.firstChild.data).toBe(props.items[i][j].value);
      })
    })
  });

  test('should show name of hook.html', () => {
    const $ = cheerio.load(renderFormPage(props));
    const h3 = $('#name')[0];
    expect(h3.firstChild.data).toBe(props.name);
  });
})
