import numpy as np
import xlrd
import pandas as pd





def daylength(dayOfYear, lat):
    latInRad = np.deg2rad(lat)
    declinationOfEarth = 23.45*np.sin(np.deg2rad(360.0*(283.0+dayOfYear)/365.0))
    if -np.tan(latInRad) * np.tan(np.deg2rad(declinationOfEarth)) <= -1.0:
        return 24.0
    elif -np.tan(latInRad) * np.tan(np.deg2rad(declinationOfEarth)) >= 1.0:
        return 0.0
    else:
        hourAngle = np.rad2deg(np.arccos(-np.tan(latInRad) * np.tan(np.deg2rad(declinationOfEarth))))
        return 2.0*hourAngle/15.0

# Load the xlsx file
excel_data = pd.read_excel('spisok.xlsx')
# Read the values of the file in the dataframe
data = pd.DataFrame(excel_data)


cols = ["10","20","30","40","50","60","70","80","90","100","110","120","130","140","150","160","170","180","190","200","210","220","230","240","250","260","270","280","290","300","310","320","330","340","350","360"]
for col in cols:
    data[col] = data.apply(
        lambda row: daylength(float(col), row['Широта']),
        axis=1
    )
#print(data.columns)
# Print the content
print("The content of the file is:\n", data)

data.drop('10')