import joblib
import re
from os.path import dirname, join

def load_model_and_vectorizer(pkl_file_path):
    filename = join(dirname(__file__), pkl_file_path)
    # Load the model and vectorizer from the PKL file
    model_and_vectorizer = joblib.load(filename)
    model = model_and_vectorizer[0]
    vectorizer = model_and_vectorizer[1]
    return model, vectorizer


def preprocess_text(text):
    # Convert text to lowercase
    text = text.lower()
    # Remove digits
    text = re.sub(r'\d+', '', text)
    # Remove extra whitespaces
    text = re.sub(r'\s+', ' ', text).strip()
    return text


def predict(input_text):
    model, vectorizer = load_model_and_vectorizer("random_forest_model.pkl")

    # Preprocess the input text
    preprocessed_text = preprocess_text(input_text)

    # Vectorize the preprocessed text
    vectorized_text = vectorizer.transform([preprocessed_text])

    # Predict probability using the pre-trained model
    probability = model.predict_proba(vectorized_text)[:, 1][0]  # Probability of class 1
    return str(probability)