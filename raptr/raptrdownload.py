from __future__ import print_function
from lxml import html
from lxml import etree
import requests


###############################################################
#####################way1
##########################################
#game-hours
def getDameData(doc, f2):
	titles = list(find_all(doc,"game-title"))
	hours = list(find_all(doc,"game-hours"))
	for title,hour in zip(titles,hours):
		gtitle=doc[title:title+80]
		gtitle=gtitle[gtitle.find(">")+1:gtitle.find("<")]
		ghour=doc[hour:hour+100]
		ghour=ghour[ghour.find("strong")+7:ghour.rfind("strong")-4]
		print(gtitle,";",ghour,file=f2)

def find_all(a_str, sub):
    start = 0
    while True:
        start = a_str.find(sub, start)
        if start == -1: return
        yield start
        start += len(sub) # use start += 1 to find overlapping matches

#esto fusko!, parseo a pelo del texto a tomar por culo
def offline():
	f4 = open('page','r')
	page=f4.read()
	f2 = open('gamedatahours.csv','w')
	getDameData(page,f2)

def online():
	#el code de abajo sacarlo con chrome, de una sesion abierta, navegar pulsando los numeros y la peticion de module tal copiarla como url y palante
	#page = requests.get('http://raptr.com/Jstyl_8/games?sort_by=hours_played')
	page = requests.get('http://raptr.com/ws/module?login_user_id=eb0a727fbf8d9121485f80d698e35656&target_user_id=eb0a727fbf8d9121485f80d698e35656&sort_by=hours_played&page=1&items_per_page=500&platforms=YTowOnt9&ranks=YTowOnt9&genres=YTowOnt9&is_ghost=&max_carousel_friends=6&format=application%2Fjson&id=UserGamesMod&method=update')
	doc = html.fromstring(page.content)
	f2 = open('gamedatahours.csv','w')
	getDameData(page.text,f2)
	f3 = open('page','w')
	print(page.text,file=f3)

online()
####################################################################################
#####################WAY2
###############################################################
#FAIL intentado hacer esto, el codigo "html" esta roto
# page = requests.get('http://econpy.pythonanywhere.com/ex/001.html')
# tree = html.fromstring(page.content)

# #This will create a list of buyers:
# buyers = tree.xpath('//div[@title="buyer-name"]/text()')
# #This will create a list of prices
# prices = tree.xpath('//span[@class="item-price"]/text()')

# print('Buyers: ', buyers)
# print('Prices: ', prices)


#build_text_list = etree.XPath("//text()") # lxml.etree only!

# usergame list
#//*[@id="B"]/div[3]/div/div[2]/ul
#//*[@id="B"]/div[3]/div/div[2]/ul/li[1]/div[1]/div[3]
def travel( doc, index, f):
	index= index + 1
	for el in doc.getchildren():
		print(index,"-",el.tag, el.get("class"), el.text, file=f)
		#getDameData (doc,f2)
		travel(el,index,f)

def fail():
	page = requests.get('http://raptr.com/ws/module?login_user_id=eb0a727fbf8d9121485f80d698e35656&target_user_id=eb0a727fbf8d9121485f80d698e35656&sort_by=hours_played&page=1&items_per_page=10&platforms=YTowOnt9&ranks=YTowOnt9&genres=YTowOnt9&is_ghost=&max_carousel_friends=6&format=application%2Fjson&id=UserGamesMod&method=update')
	doc = html.fromstring(page.content)
	main_div = doc.xpath('//li[@class="game-hours"]')
	main = {}
	tr = []
	index = 0
	#travel over files
	f = open('myfile','w')
	travel(doc, index,f)


