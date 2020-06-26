#!/usr/bin/python3
# -*- coding: UTF-8 -*-

'''
    script util para gerar as palavras para
    que sejam diretamente insteridas no xml
'''

#dict dificuldade : lista de palavras
dificuldade = {}
#dict palavra : descricao
par = {}

#estrutura do campo a ser adicionado ao xml
form = "<extra\n\
        android:name=\"{}\"\n\
        android:value=\"{}\" />"
item = "\t<item>{}<item/>"


arq = open('entrada.txt','r');

texto = arq.readlines();
txt_size=len(texto);
linha = 0;
dif_atual = ''

while linha < txt_size:
    #exclui linhas em branco
    if texto[linha] == '\n':
        texto.pop(linha);
        txt_size-=1;
    else:
        #tmp e a variavel onde armazenamos as informacoes desejadas extraidas
        tmp = texto[linha].strip('\n');
        tmp = tmp.split(':');
        if len(tmp)==1: 
            tmp = tmp[0].strip()
            if tmp not in par.keys():
                dif_atual = tmp;
                dificuldade[dif_atual] = []
            else:
                if tmp not in dificuldade[dif_atual]:
                    dificuldade[dif_atual].append(tmp);
        else:
            tmp[0] = tmp[0].strip();
            tmp[1] = tmp[1].strip();
            par[tmp[0]] = tmp[1];
            dificuldade[dif_atual].append(tmp[0]);
        linha+=1;

for dif in dificuldade.keys():
    print('<!--',dif,'-->');
    for pal in dificuldade[dif]:
        print(item.format(pal));

for pal in par.keys():
    print(form.format(pal, par[pal]));
