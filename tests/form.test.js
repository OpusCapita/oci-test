const cheerio = require('cheerio')
const pug = require('pug');
const renderFormPage = pug.compileFile('./views/form.pug');

const form = {
  name: "test-form",
  fields: [
    {
      id: 'input-1',
      value: 'input-1',
    },
    {
      id: 'input-2',
      value: 'input-2',
    },
    {
      id: 'undefined',
      value: 'null',
    },
    {
      id: '',
      value: 'input-4(*^%@!)!)()#@><"?>',
    },
    {
      id: 'inp123#$%^&*()ut-5',
      value: '',
    },
    {
      id: 'oci-test/>?',
      value: 'httpas>',
    },
  ]
}

describe('form.html', () => {
  test('should render form.html inputs', () => {
    const $ = cheerio.load(renderFormPage(form));
    Array.from($('input[type=text]')).forEach((input, index) => {
      console.log(input);
      expect(input.attribs.value).toBe(form.fields[index].value);
      expect(input.attribs.name).toBe(form.fields[index].id);
    })
  })

  test('should show name of form.html', () => {
    const $ = cheerio.load(renderFormPage(form));
    const h3 = $('#name')[0];
    expect(h3.firstChild.data).toBe(form.name);
  });
})
