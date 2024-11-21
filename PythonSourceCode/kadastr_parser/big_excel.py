import os
import pandas as pd
files = os.listdir("Массовое_формирование_претензий_20241022_14700 (1)")


all_data = pd.DataFrame()
for file in files:
    if file.endswith('.xlsx'):
        df = pd.read_excel("Массовое_формирование_претензий_20241022_14700 (1)"+"\\"+file)
        all_data = all_data.append(df, ignore_index=True)
all_data.head()
status = pd.read_excel('test.xlsx')
all_data_st = pd.merge(all_data, status, how='left')
all_data_st.head()
all_data_st.to_excel('res.xlsx')