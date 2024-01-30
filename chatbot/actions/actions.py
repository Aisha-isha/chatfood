from typing import Any, Dict, List, Text, Optional
from rasa_sdk import Action, Tracker
from rasa_sdk.executor import CollectingDispatcher
from rasa_sdk.events import SlotSet
import csv



import mysql.connector



class ActionStoreAllergy(Action):
    def name(self) -> Text:
        return "action_store_allergy"

    def run(self, dispatcher: CollectingDispatcher, tracker: Tracker, domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:
        user_input = tracker.latest_message.get('text')
        allergy_i = user_input.lower().replace('i can\'t eat ', '').strip()
        allergy = allergy_i.lower().replace('i have allergy from ', '').strip()
        # id_user = tracker.get_slot("userId")
        # dispatcher.utter_message(f"{user_input}")

        if allergy:
            # Call the ActionSetUserId action to set the userId slot

            db_connection = mysql.connector.connect(
                host="localhost",
                user="root",
                password="oracle",
                database="pfa"
            )

            insert_query = "INSERT INTO plan (allergy) VALUES ( %s)"
            data = [allergy]

            try:
                cursor = db_connection.cursor()
                cursor.execute(insert_query, data)
                db_connection.commit()
                # dispatcher.utter_message(f"Your allergies '{allergy}' have been stored successfully. I will give you a food planner.")
                dispatcher.utter_message(f"Your allergies have been stored successfully. I will give you a food planner.")

            except mysql.connector.Error as error:
                dispatcher.utter_message(f"An error occurred: {error}")
            finally:
                cursor.close()
                db_connection.close()
        else:
            dispatcher.utter_message("Unable to detect the allergy.")

        return []



#===========================================================


class ActionNoAllergies(Action):
    def name(self) -> Text:
        return "no_allergies"

    def run(self, dispatcher: CollectingDispatcher, tracker: Tracker, domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:
        db_connection = mysql.connector.connect(
            host="localhost",
            user="root",
            password="oracle",
            database="pfa"
        )

        # Insérer les données dans la table pfa
        is_yes='yes'
        db_cursor = db_connection.cursor()
        insert_query = "INSERT INTO plan (is_yes) VALUES (%s)"
        data = (is_yes,)
        db_cursor.execute(insert_query, data)
        db_connection.commit()

        db_cursor.close()
        db_connection.close()

        dispatcher.utter_message(text="Okay! I will plan")
        return []


class ActionSearchCalories(Action):
    def name(self) -> Text:
        return "action_get_calories"

    def run(self, dispatcher: CollectingDispatcher, tracker: Tracker, domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:
        user_input = tracker.latest_message.get('text')
        food = user_input.lower().replace('how much', '').strip()
        food_ = food.lower().replace('calories in', '').strip()
        food_item = food_.lower().replace('calories', '').strip()

        if food_item:
            with open('C:/Users/Aicha/Desktop/chatbot/data/food.csv', 'r') as file:
                reader = csv.DictReader(file)
                for row in reader:
                    if row['foodstuff'].lower() == food_item:
                        calories = row['Kilocalories']
                        fiber = row['Fiber']
                        sugar = row['Sugar']
                        protein = row['Protein']
                        dispatcher.utter_message(text=f"{food_item} : \n Calories: {calories} kcal, \n Fiber: {fiber}, \n Sugar: {sugar}, \n Protein: {protein}")
                        break
                else:
                    dispatcher.utter_message(text="Calories information not found for the given food item.")
        else:
            dispatcher.utter_message(text="Please provide a food item.")

        return []

class ActionSearchIngredients(Action):
    def name(self) -> Text:
        return "action_get_ingredients"

    def run(self, dispatcher: CollectingDispatcher, tracker: Tracker, domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:
        user_input = tracker.latest_message.get('text')
        food = user_input.lower().replace('ingredients', '').strip()
        foody = food.lower().replace('of', '').strip()
        food_i = foody.lower().replace('the', '').strip()
        food_item = food_i.lower().replace('give me', '').strip()

        if food_item:
            with open('C:/Users/Aicha/Desktop/chatbot/data/meal.csv', 'r', encoding='utf-8') as file:
                reader = csv.DictReader(file)
                for row in reader:
                    meal = row['name_meal'].replace('"', '').lower()
                    if meal == food_item:
                        ingredients = row['ingrediant_meal']
                        formatted_ingredients = ',\n'.join(ingredients.split(','))
                        dispatcher.utter_message(text=f"{food_item} Ingredients:     \n{formatted_ingredients} ")
                        break
                else:
                    dispatcher.utter_message(text="Meal not found for the given food item.")
        else:
            dispatcher.utter_message(text="Please provide a food item.")

        return []

# ==========================================================================================================================

import random

class ActionRecommendBreakfast(Action):
    def name(self) -> Text:
        return "action_recommend_breakfast"

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:
        
        data_file = r"C:\Users\Aicha\Desktop\chatbot\data\meal.csv"  # Path to the data.csv file
        
        # Read the CSV file and extract the values from the name_meal column where time_meal is 'breakfast'
        with open(data_file, "r") as file:
            csv_reader = csv.DictReader(file)
            meal_database = [row["name_meal"] for row in csv_reader if row["time_meal"] == "breakfast"]
        
        recommended_meals = random.sample(meal_database, 3)
        recommended_meals = "\n".join([f"{i+1}. {meal}" for i, meal in enumerate(recommended_meals)])
        
        message = "Sure! Here are some breakfast meal recommendations:\n{}".format(recommended_meals)
        dispatcher.utter_message(text=message)
        
        return []



import csv

# class ActionStoreAllergy(Action):
#     def name(self) -> Text:
#         return "action_store_preference"

#     def run(
#         self, dispatcher: CollectingDispatcher, tracker: Tracker, domain: Dict[Text, Any]
#     ) -> List[Dict[Text, Any]]:
#         user_input = tracker.latest_message.get('text')
#         preference = user_input.lower().replace('I prefer eating', '').strip()

#         if preference:
#             # Split the preference into a list of individual preferences
#             preferences = preference.split(',')

#             # Read the CSV file
#             with open(r"C:\Users\Aicha\Desktop\chatbot\data\meal.csv", 'r') as file:
#                 reader = csv.DictReader(file)
#                 breakfast_meals = []

#                 # Iterate through each row in the CSV file
#                 for row in reader:
#                     # Check if the time_meal is "breakfast" and if any preference matches the ingredient_meal
#                     if row['time_meal'] == 'breakfast' and any(pref in row['ingrediant_meal'].split(',') for pref in preferences):
#                         breakfast_meals.append(row['name_meal'])


#                         # Break the loop if two breakfast meals have been found
#                         if len(breakfast_meals) == 1:
#                             break
#                     else:
#                         print('random')    

#             # Do something with the retrieved breakfast meals
#             if breakfast_meals:
#                 # You can access the breakfast_meals list here and use it as needed
#                 print(breakfast_meals)
#             else:
#                 print("nooooo")

#         return []






# class ActionStorePreference(Action):
#     def name(self) -> Text:
#         return "action_store_preference"

#     def run(
#         self, dispatcher: CollectingDispatcher, tracker: Tracker, domain: Dict[Text, Any]
#     ) -> List[Dict[Text, Any]]:
#         user_input = tracker.latest_message.get('text')
#         preference = user_input.lower().replace('i prefer to eat ', '').strip()

#         if preference:
#             # Split the preference into a list of individual preferences
#             preferences = preference.split(',')

#             # Read the CSV file
#             with open("data/meal.csv", 'r') as file:
#                 reader = csv.DictReader(file)
#                 matching_meals = []

#                 # Iterate through each row in the CSV file
#                 for row in reader:
#                     # Check if all preferences are present in the ingredient_meal
#                     if all(pref in row['ingredient_meal'].split(',') for pref in preferences):
#                         matching_meals.append(row['name_meal'])

#                 # Do something with the matching meals
#                 if matching_meals:
#                     # You can access the matching_meals list here and use it as needed
#                     dispatcher.utter_message(text=', '.join(matching_meals))
#                 else:
#                     dispatcher.utter_message(text="Random")
#         else:
#             dispatcher.utter_message(text="No preferences provided")

#         return []


    # ===================================================================

class ActionRecommendLunch(Action):
    def name(self) -> Text:
        return "action_recommend_lunch"

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:
        
        data_file = r"C:\Users\Aicha\Desktop\chatbot\data\meal.csv"  # Path to the data.csv file
        
        # Read the CSV file and extract the values from the name_meal column where time_meal is 'breakfast'
        with open(data_file, "r") as file:
            csv_reader = csv.DictReader(file)
            meal_database = [row["name_meal"] for row in csv_reader if row["time_meal"] == "lunch"]
        
        recommended_meals = random.sample(meal_database, 3)
        recommended_meals = "\n".join([f"{i+1}. {meal}" for i, meal in enumerate(recommended_meals)])
        
        message = "Sure! Here are some lunch meal recommendations:\n{}".format(recommended_meals)
        dispatcher.utter_message(text=message)
        
        return []
    
class ActionRecommendDinner(Action):
    def name(self) -> Text:
        return "action_recommend_dinner"

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:
        
        data_file = r"C:\Users\Aicha\Desktop\chatbot\data\meal.csv"  # Path to the data.csv file
        
        # Read the CSV file and extract the values from the name_meal column where time_meal is 'breakfast'
        with open(data_file, "r") as file:
            csv_reader = csv.DictReader(file)
            meal_database = [row["name_meal"] for row in csv_reader if row["time_meal"] == "dinner"]
        
        recommended_meals = random.sample(meal_database, 3)
        recommended_meals = "\n".join([f"{i+1}. {meal}" for i, meal in enumerate(recommended_meals)])
        
        message = "Sure! Here are some dinner meal recommendations:\n{}".format(recommended_meals)
        dispatcher.utter_message(text=message)
        
        return []